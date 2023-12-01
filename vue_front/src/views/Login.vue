<style src="../assets/css/Login.css"></style>

<template>
    <gnbBar />
    <div class="login">
        <div class="title-bar">
            <div class="login_header_logo">
                <a href="/"><img src="../assets/img/logo.png" alt=""></a>
            </div>
        </div>

        <div class="login_wrap">
            <form class="loginform" @submit.prevent="loginForm">
                <input v-model="email" type="text" id="username" :class="{ error_border: error_border_check }"
                    placeholder="이메일" />
                <input ref="passwordVisible" v-model="password" type="password" id="password"
                    :class="{ error_border: error_border_check }" placeholder="비밀번호" />
                <img :src="passwordVisibleSrc[passwordVisibleIndex]" class="password_visible"
                    @click="passwordVisibleToggle" />

                <p id="login_error" v-if="email_check">
                <div class="circle">
                    <span>!</span>
                </div>
                이메일주소를 정확히 입력해주세요. 예)abcd@naver.com
                </p>
                <p id="login_error" v-else-if="password_check">
                <div class="circle">
                    <span>!</span>
                </div>
                비밀번호를 정확히 입력해주세요.<br />
                *8자리 이상 영문, 숫자, 특수문자가 각각 1개 이상
                </p>
                <div class="user_login_btns">
                    <a href="#">
                        <input @click="loginValidation" class="login_submit" type="submit" id="login" value="로그인" /></a>
                    <a class="join_btn" href="/join">회원가입</a>
                </div>
            </form>
        </div>

        <div class="login_wrap2">
            <form method="post">
                <div class="user_social_btns">
                    <div class="user_social_btns_p">
                        <p>소셜로 간편하게 로그인하세요</p>
                    </div>
                    <a class="social_btn naver" href="/naverlogin"><img class="social_btn_naver"
                            src="../assets/img/naver.png" alt=""></a>
                    <a class="social_btn kakao" @click="kakaoLogin"><img class="social_btn_kakao"
                            src="../assets/img/kakao.png" alt=""></a>
                </div>
            </form>
        </div>
    </div>
    <!-- <Footer /> -->
</template>
  
<script>
  import gnbBar from "../components/gnbBar.vue";
//   import Footer from '../components/footer.vue';

import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

export default {
    data() {
        return {
            email: "",
            password: "",
            passwordVisible: false,
            passwordVisibleSrc: [
                require("../assets/img/visible.png"),
                require("../assets/img/novisible.png")
            ],
            passwordVisibleIndex: 0,

            email_check: false,
            password_check: false,
            error_border_check: false,

            allcheck: true,
            allcheck1: true,
            allcheck2: true,
        };
    },
    components: { gnbBar },

    watch: {
        email: function () {
            this.checkEmail();
            this.inputAllCheck();
        },
        password: function () {
            this.checkPassword();
            this.inputAllCheck();
        },
    },
    methods: {
        passwordVisibleToggle() {
            this.passwordVisibleIndex = (this.passwordVisibleIndex + 1) % this.passwordVisibleSrc.length
            if (!this.passwordVisible) {
                this.$refs.passwordVisible.type = "text";
                this.passwordVisible = true;
            } else {
                this.$refs.passwordVisible.type = "password";
                this.passwordVisible = false;
            }
        },
        checkEmail() {
            // 이메일 형식 검사
            const validateEmail =
                /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

            if (this.email === "" || !validateEmail.test(this.email) || !this.email) {
                this.email_check = true;
                this.error_border_check = true;
                this.allcheck1 = true; //입력조건 안맞을시 true
            } else {
                this.email_check = false;
                this.error_border_check = false;
                this.allcheck1 = false;
            }
        },
        checkPassword() {
            // 최소 8자리 이상 영문 대소문자, 숫자, 특수문자가 각각 1개 이상
            const validatePassword =
            /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$/;

            if (
                this.password === "" ||
                !validatePassword.test(this.password) ||
                !this.password
            ) {
                this.password_check = true;
                this.error_border_check = true;
                this.allcheck2 = true;
            } else {
                this.password_check = false;
                this.error_border_check = false;
                this.allcheck2 = false;
            }
        },
        inputAllCheck() {
            if (this.allcheck1 || this.allcheck2) {
                //하나라도 입력조건이 안맞을시
                this.allcheck = true; //버튼 비활성
            } else {
                this.allcheck = false;
            }
        },
        loginForm() {
            //백엔드로 로그인정보 전달
            axios({
                url: "http://localhost:3000/auth/login",
                method: "POST",
                data: {
                    USER_EMAIL: this.email,
                    USER_PW: this.password,
                },
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then((res) => {
                    console.log(Array.isArray(res.data));
                    console.log(res.data.USER_EMAIL);
                    if(Array.isArray(res.data) && res.data.length === 0) {
                        alert("잘못된 아이디/비밀번호 이거나 회원이 아닙니다.")
                    } else {

                        const user = res.data[0];
                        localStorage.setItem("userID", user.USER_EMAIL);
                        localStorage.setItem("userNick", user.USER_NAME);
                        localStorage.setItem("userProvider", user.USER_PROVIDER);
                        
                        window.location.href = "/"; 

                    }
                })
                .catch((err) => {
                    alert(err);
                });
        },
        kakaoLogin() {
            window.Kakao.Auth.login({
                scope:
                    "profile_nickname, profile_image, account_email, gender, age_range",
                success: this.getProfile,
            });
        },
        getProfile(autoObj) {
            window.Kakao.API.request({
                url: "/v2/user/me",
                success: (res) => {
                    const kakao_account = res.kakao_account;
                    this.login(kakao_account);
                },
            });
        },
        async login(kakao_account) {
            const email = kakao_account.email;

            await axios({
                url: "http://localhost:3000/auth/kakaologin",
                method: "POST",
                data: {
                    email: kakao_account.email,
                    name: kakao_account.profile.nickname,
                    sex: kakao_account.gender,
                    agegroup: kakao_account.age_range,
                    provider: "kakao",
                },
            }).then(async (res) => {
                this.pullData(email);
            });
        },
        pullData(email) {
            axios({
                url: "http://localhost:3000/auth/kakaoData",
                method: "POST",
                data: {
                    email: email,
                },
            }).then(async (res) => {
                localStorage.setItem("userID", res.data.email);
                localStorage.setItem("userNick", res.data.nick);
                localStorage.setItem("userProvider", res.data.provider);

                window.location.href = "/";
            });
        },
    },
};
</script>
  
  