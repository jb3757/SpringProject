<style src="../assets/css/Join.css"></style>

<template>
    <gnbBar />
    <div class="join">
        <div class="title-bar">
            <div class="join_header_logo">
                <a href="/"><img src="../assets/img/logo.png" alt=""></a>
            </div>
        </div>

        <!-- 소셜로그인 -->
        <div class="join_wrap2">
            <form method="post">
                <div class="user_social_btns">
                    <div class="user_social_btns_p">
                        <p>소셜로 간편하게 로그인하세요</p>
                    </div>
                    <a class="social_btn" href="/naverlogin"><img class="social_btn_naver" src="../assets/img/naver.png"
                            alt=""></a>
                    <a class="social_btn" @click="kakaoLogin"><img class="social_btn_kakao" src="../assets/img/kakao.png"
                            alt=""></a>
                </div>
            </form>
        </div>

        <!-- 로컬회원가입 -->
        <div class="join_wrap">
            <p><span class="fontRed">*</span> 필수입력 항목</p>
            <form @submit.prevent="joinForm">
                <label for="name">이름<span class="fontRed">*</span></label>
                <input v-model="name" type="text" id="name" placeholder="이름 입력" :class="{ error_border: name_check }"
                    maxlength="25"><br />
                <label for="email">아이디<span class="fontRed">*</span></label>
                <input :disabled="!email_auth_check" v-model="emailFirst" type="text" id="email" placeholder="이메일 입력"
                    :class="{ error_border: email_check || emailcheck == 2 }" maxlength="25">
                <select :disabled="!email_auth_check" v-model="emailSecond" class="email_list" name="emailList"
                    id="emailList">
                    <option value="@naver.com">@naver.com</option>
                    <option value="@hanmail.net">@hanmail.net</option>
                    <option value="@gmail.com">@gmail.com</option>
                    <option value="@nate.com">@nate.com</option>
                    <option value="@hotmail.com">@hotmail.com</option>
                </select>
                <!-- <button type="button" @click=" startCountdown(), sendEmail()" :disabled="emailcheck != 2"
                    class="email_auth">이메일 인증</button> -->
                <div v-if="clickSendEmail && emailcheck == 2" class="email_auth_complete">
                    <input class="email_auth_complete_input" v-model="userVerifyNum" type="text" maxlength="6">
                    <span>{{ formattedTime }}</span>
                    <button type="button" @click="completeAuthEmail">인증완료</button>

                </div>
                <form @submit.prevent="emailCheckForm">
                    <a href="/auth/checkemail"><button type="submit" id="email_check"
                            class="username_submit">중복확인</button></a>
                </form>
                <p id="error" v-if="email_check">이메일주소를 정확히 입력해주세요. 예)abcd@naver.com</p>
                <p id="error" v-show="emailcheck == 1">존재하는 이메일입니다.</p>
                <p id="complete" v-show="emailcheck == 2 && !email_check">사용가능한 이메일입니다.</p>
                <label for="password">비밀번호<span class="fontRed">*</span></label>
                <input v-model="password" type="password" id="password" :class="{ error_border: password_check }"
                    placeholder="비밀번호 입력" maxlength="15"><br />
                <p id="error" v-if="password_check">비밀번호를 정확히 입력해주세요.<br /> *8자리 이상 영문, 숫자, 특수문자가 각각 1개 이상</p>
                <label for="password_check2">비밀번호 확인<span class="fontRed">*</span></label>
                <input v-model="password2" type="password" id="password_check" :class="{ error_border: password_check2 }"
                    placeholder="비밀번호 확인 입력"><br />
                <p id="error" v-if="password_check2">비밀번호가 일치하지 않습니다.</p>
                <div class="gender">
                    <label for="sex">성별<span class="fontRed">*</span></label>
                    <input v-model="sex" type='radio' name='gender' value='m' class="input_sex" />남자
                    <input v-model="sex" type='radio' name='gender' value='f' class="input_sex" />여자
                </div>
                <p id="error" v-if="sex_check">성별을 선택하세요.</p>
                <div class="age_range">
                    <label for="age_range">나이대<span class="fontRed">*</span></label>
                    <input v-model="agegroup" type='radio' name='agegroup' value='10' class="input_sex" />10대
                    <input v-model="agegroup" type='radio' name='agegroup' value='20' class="input_sex" />20대
                    <input v-model="agegroup" type='radio' name='agegroup' value='30' class="input_sex" />30대
                    <input v-model="agegroup" type='radio' name='agegroup' value='40' class="input_sex" />40대
                    <input v-model="agegroup" type='radio' name='agegroup' value='50' class="input_sex" />50대
                    <input v-model="agegroup" type='radio' name='agegroup' value='60' class="input_sex" />60세 이상
                </div>
                <p id="error" v-if="age_range_check">나이대를 선택하세요.</p>
                <label for="phone_num">전화번호<span class="fontRed">*</span></label>
                <input v-model="phone_num" @input="formatPhoneNumber" type="text" id="phone_num" placeholder="전화번호 입력"
                    :class="{ error_border: phone_check }" maxlength="13"><br />
                <p id="error" v-if="phone_check">전화번호를 정확히 입력해주세요.</p>
                <label for="epostNum">우편번호</label>
                <input v-model="epostNum" ref="epostNum" type="text" id="epostNum" placeholder="우편번호 입력">
                <input @click="sample6_execDaumPostcode" class="epostNumBtn" type="button" value="우편번호 검색"><br />
                <label for="epostAdress">주소</label>
                <input v-model="epostAdress" ref="epostAdress" type="text" id="epostAdress" placeholder="주소 입력"><br />
                <label for="epostDetailAdress">상세주소</label>
                <input v-model="epostDetailAdress" ref="epostDetailAdress" type="text" id="epostDetailAdress"
                    placeholder="상세주소 입력"><br />
                <!-- 가입하기버튼 -->
                <input type="submit" :class="{ 'error_submit': allcheck, 'submit': !allcheck }" id="login" value="가입하기">
            </form>
        </div>
    </div>
    <!-- <Footer /> -->
</template>

<script>
  import gnbBar from "../components/gnbBar.vue";
// import Footer from '../components/footer.vue'

import axios from 'axios'
axios.defaults.baseURL = 'http://localhost:3000';
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

export default {
    components: { gnbBar },
    data() {
        return {
            // 회원 입력정보
            name: '',
            email: '',
            emailFirst: '',
            emailSecond: '',
            password: '',
            password2: '',
            phone_num: '',
            sex: '',
            agegroup: '',


            name_check: true,
            email_check: false,
            password_check: false,
            password_check2: false,
            sex_check: false,
            age_range_check: false,
            phone_check: false,
            error_border_check: false,

            //이메일 인증하기
            clickSendEmail: false,
            countdown: 180,
            interval: null,
            verifyNum: '',
            userVerifyNum: '',
            email_auth_check: true,

            //주소 데이터
            epostNum: "",
            epostAdress: "",
            epostDetailAdress: "",
            epostreference: "",

            allcheck: true,
            allcheck1: true,
            allcheck2: true,
            allcheck3: true,
            allcheck4: true,
            allcheck5: true,
            allcheck6: true,
            allcheck7: true,
            emailcheck: 3,
            nicknamecheck: 3,
        };
    },
    watch: {
        'name': function () {
            this.checkName()
            this.inputAllCheck()
        },
        'emailFirst': function () {
            this.checkEmail()
            this.funcWatch()
        },
        'emailSecond': function () {
            this.checkEmail()
            this.funcWatch()
        },
        'password': function () {
            this.checkPassword()
            this.inputAllCheck()
        },
        'password2': function () {
            this.checkPassword2()
            this.inputAllCheck()
        },
        'sex': function () {
            this.checksex()
            this.inputAllCheck()
        },
        'agegroup': function () {
            this.checkage_range()
            this.inputAllCheck()
        },
        'phone_num': function () {
            this.checkphone()
            this.inputAllCheck()
        }

    },
    computed: {
        formattedTime() {
            const minutes = Math.floor(this.countdown / 60);
            const seconds = this.countdown % 60;
            return `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
        },
    },
    methods: {
        funcWatch() {
            this.emailCheckForm()
            this.inputAllCheck()
        },
        movetomain() {
            window.location.href = '/';
        },
        checkName() {
            console.log(this.countdown);
            if (this.name === '' || !this.name) {
                this.name_check = true;
                this.error_border_check = true;
                this.allcheck1 = true;
            } else {
                this.name_check = false;
                this.error_border_check = false;
                this.allcheck1 = false;
            }
        },
        checkEmail() {
            // 이메일 형식 검사
            this.email = this.emailFirst + this.emailSecond;
            const validateEmail1 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
            const validateEmail2 = /^[a-zA-Z0-9]*$/;
            console.log(this.email);
            if (this.email === '' || !validateEmail1.test(this.email) || !this.email || !validateEmail2.test(this.emailFirst)) {
                this.email_check = true;
                this.error_border_check = true;
                this.allcheck2 = true; //입력조건 안맞을시 true
            } else {
                this.email_check = false;
                this.error_border_check = false;
                this.allcheck2 = false;
            }
        },
        checkPassword() {
            // 최소 8자리 이상 영문, 숫자, 특수문자가 각각 1개 이상
            const validatePassword = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$/

            if (this.password === '' || !validatePassword.test(this.password) || !this.password) {
                this.password_check = true;
                this.error_border_check = true;
                this.allcheck3 = true;
            } else {
                this.password_check = false;
                this.error_border_check = false;
                this.allcheck3 = false;
            }
        },
        checkPassword2() {
            if (this.password === this.password2) {
                this.password_check2 = false;
                this.error_border_check = false;
                this.allcheck4 = false;
            } else {
                this.password_check2 = true;
                this.error_border_check = true;
                this.allcheck4 = true;
            }
        },
        checksex() {
            if (this.sex == null) { //선택했으면
                this.allcheck5 = true;
            } else {
                this.allcheck5 = false;
            }
        },
        checkage_range() {
            if (this.agegroup == null) {
                this.allcheck6 = true;
            } else {
                this.allcheck6 = false;
            }
        },
        checkphone() {
            const validatephone = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

            if (this.phone_num === '' || !validatephone.test(this.phone_num) || !this.phone_num) {
                this.phone_check = true;
                this.error_border_check = true;
                this.allcheck7 = true;
            } else {
                this.phone_check = false;
                this.error_border_check = false;
                this.allcheck7 = false;
            }
        },
        formatPhoneNumber() {
            // 숫자 이외의 문자 제거
            this.phone_num = this.phone_num.replace(/[^\d-]/g, '');
            // 하이픈(-) 삽입
            if (this.phone_num.length > 2) {
                this.phone_num = this.phone_num.replace(/^01([0|1|6|7|8|9])-?(\d{4})-?(\d{4})$/, '01$1-$2-$3');
            }
        },
        inputAllCheck() {
            if (this.allcheck1 || this.allcheck2 || this.allcheck3 || this.allcheck4 || this.allcheck5 || this.allcheck6 || this.allcheck7 || this.email_check) { //하나라도 입력조건이 안맞을시
                this.allcheck = true; //버튼 비활성
            } else {
                this.allcheck = false;
            }
        },
        // async sendEmail() {
        //     await axios({
        //         url: "http://localhost:3000/auth/sendEmail",
        //         method: "POST",
        //         data: {
        //             email: this.email
        //         },
        //     }).then(async (res) => {
        //         this.verifyNum = res.data.verifyNum;
        //         console.log(res.data.verifyNum, "서버 코드");
        //     }).catch(error => {
        //         alert(error);
        //     })
        // },
        startCountdown() {
            this.clickSendEmail = true;
            this.interval = setInterval(() => {
                if (this.countdown > 0) {
                    this.countdown--;
                } else {
                    alert('인증에 실패하였습니다.');
                    clearInterval(this.interval); // 컴포넌트가 제거되기 전에 interval을 정리해야합니다.
                    this.countdown = 180;
                    this.userVerifyNum = "";
                    this.clickSendEmail = false; // 인증번호입력창 초기화
                }
            }, 1000);
        },
        // completeAuthEmail() {
        //     console.log(this.userVerifyNum, "사용자입력 코드");
        //     if (this.verifyNum == this.userVerifyNum && !this.userVerifyNum == "") {
        //         alert("인증이 완료되었습니다.");
        //         this.email_auth_check = false;
        //         this.clickSendEmail = false; // 인증번호입력창 초기화
        //         this.userVerifyNum = ""; //사용자입력 인증코드 초기화
        //         this.countdown = 180; // 인증시간 초기화
        //         clearInterval(this.interval); // 컴포넌트가 제거되기 전에 interval을 정리해야합니다.
        //     } else {
        //         alert("인증번호가 일치하지 않습니다.");
        //         this.email_auth_check = true;
        //         this.userVerifyNum = ""; //사용자입력 인증코드 초기화
        //     }
        //     this.inputAllCheck()
        // },
        async emailCheckForm() {
            await axios({
                url: "http://localhost:3000/auth/checkemail",
                method: "GET",
                params: {
                    email: this.email,
                },
            }).then(async (res) => {
                const validateEmail = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
                // console.log(res.data.result);
                // console.log(this.emailcheck);
                if (res.data.result === 0 && validateEmail.test(this.email)) {
                    this.emailcheck = 2;
                } else if (res.data.result !== 0) {
                    this.emailcheck = 1;
                    this.allcheck = true;
                    // this.email_auth_check = false;
                } else if (!this.email) {
                    this.emailcheck = 3;
                }
            }).catch(error => {
                alert(error);
            })
        },

        sample6_execDaumPostcode() { // 다음 지도API
            new daum.Postcode({
                oncomplete: (data) => {
                    let addr = ''; // 주소 변수

                    if (data.userSelectedType === 'R') {
                        addr = data.roadAddress;
                        // 우편번호와 주소 정보를 해당 필드에 넣는다.
                        this.epostNum = data.zonecode;
                        this.epostAdress = addr;
                    } else if (data.userSelectedType === 'J') {
                        addr = data.jibunAddress;
                        // 우편번호와 주소 정보를 해당 필드에 넣는다.
                        this.epostNum = data.zonecode;
                        this.epostAdress = addr;
                    }

                    // 상세주소가 비어있지 않을 때만 기존의 상세주소를 유지하도록 처리
                    if (this.epostDetailAdress !== '') {
                        document.getElementById('epostDetailAdress').value = this.epostDetailAdress;
                    }

                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById('epostDetailAdress').focus();
                }
            }).open();
        },
        async joinForm() { //백엔드로 회원가입 정보 전달
            // if (this.allcheck == true) {
            //     alert("회원정보를 확인해주세요.");
            // } else {
                await axios({
                    url: "http://localhost:3000/auth/join",
                    method: "POST",
                    data: {
                        USER_NAME: this.name,
                        USER_EMAIL: this.email,
                        USER_PW: this.password,
                        USER_SEX: this.sex,
                        USER_AGEGROUP: this.agegroup,
                        USER_PHONE: this.phone_num,
                        USER_ZIPCODE: this.epostNum,
                        USER_ADD1: this.epostAdress,
                        USER_ADD2: this.epostDetailAdress
                    },
                }).then(async (res) => {
                    console.log(res.data);
                    alert(res.data);
                    this.autoLogin();
                }).catch(error => {
                    alert(error);
                })
            // }

        },
        async autoLogin() {
            await axios({
                url: "http://localhost:3000/auth/login",
                method: "POST",
                data: {
                    USER_EMAIL: this.email,
                    USER_PW: this.password,
                },
            }).then(async (res) => {
                if(Array.isArray(res.data) && res.data.length === 0) {
                        alert("잘못된 아이디/비밀번호 이거나 회원이 아닙니다.")
                    } else {

                        const user = res.data[0];
                        localStorage.setItem("userID", user.USER_EMAIL);
                        localStorage.setItem("userNick", user.USER_NAME);
                        localStorage.setItem("userProvider", user.USER_PROVIDER);
                        
                        window.location.href = "/"; 

                    }
            }).catch((error) => {
                alert(error);
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
                    nick: kakao_account.profile.nickname,
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
}
</script> 
