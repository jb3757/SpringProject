<template>
    <gnbBar />
    <div class="mypage_menu">
        <myPageSide />
    </div>
    <div class="right_box">
        <h2 class="userUpdateTitle">회원정보 관리</h2>
        <hr class="section-divider" />

        <div class="input-group">
            <div class="input-item">
                <label for="email">아이디</label>
                <input type="text" id="email" v-model="originalData.email" :readonly="true" />
            </div>
            <div class="input-item">
                <label for="name">이름</label>
                <input type="text" id="name" v-model="originalData.name" :readonly="true" />
            </div>

            <div>
                <div class="input-item">
                    <label for="password">비밀번호</label>
                    <input
                        type="password"
                        id="password"
                        :value="isPasswordModalClosed ? '*********' : originalData.password"
                        @click="openPasswordModal"
                    />
                </div>

                <!-- 모달 요소 -->
                <div v-if="passwordModal" class="password_modal">
                    <div class="pass_modal_content">
                        <h3>비밀번호 변경</h3>
                        <div class="mypage_new_pw">
                            <label for="newPassword">새 비밀번호</label>
                            <input type="password" id="newPassword" v-model="newPassword" @input="checkNewPassword" />
                            <span class="passwordValidationMessage" v-if="passwordValidationMessage">{{ passwordValidationMessage }}</span>
                        </div>
                        <div class="mypage_new_pw">
                            <label for="confirmPassword">비밀번호 확인</label>
                            <input type="password" id="confirmPassword" v-model="confirmPassword" />
                            <span v-if="passwordMatchMessage">{{ passwordMatchMessage }}</span>
                        </div>
                        <div class="button-group-center">
                            <button class="mypage_group_first" @click="changePassword">비밀번호 변경</button>
                            <button @click="closePasswordModal">취소</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="input-item">
                <label for="sex">성별</label>
                <input type="text" id="sex" :value="convertGender(originalData.sex)" :readonly="true" />
            </div>
            <div class="input-item">
                <label for="age">나이대</label>
                <input type="number" id="age" :value="convertAgeRange(originalData.age)" :readonly="true" />
            </div>

            <div class="input-item">
                <label for="add1">주소</label>
                <input type="text" id="add1" v-model="updatedFields.add1" @click="openAddressSearch" />
            </div>
            <div class="input-item">
                <label for="add2">상세주소</label>
                <input type="text" id="add2" v-model="updatedFields.add2" />
            </div>
            <div class="input-item">
                <label for="zipcode">우편번호</label>
                <input type="text" id="zipcode" v-model="updatedFields.zipcode" />
            </div>

            <div class="input-item">
                <label for="phone_num">전화번호</label>
                <input
                    type="text"
                    id="phone_num"
                    v-model="updatedFields.phone_num"
                    @input="formatPhoneNumber(updatedFields.phone_num)"
                    maxlength="13"
                />
            </div>
        </div>

        <div class="button_group">
            <button type="button" @click="cancelUpdate">취소</button>
            <button type="button" @click="updateUserInfo">수정</button>
        </div>
    </div>
</template>
<script>
import axios from "axios";
import myPageSide from "../components/myPageSide.vue";
import gnbBar from "@/components/gnbBar.vue";

export default {
    components: { myPageSide, gnbBar },
    data() {
        return {
            originalData: {}, // 기존 데이터를 저장할 객체
            updatedFields: {}, // 수정한 데이터를 저장할 객체
            message: "",
            passwordModal: false,
            newPassword: "",
            confirmPassword: "",
            passwordValidationMessage: "",
            passwordMatchMessage: "",
            isPasswordModalClosed: false,
        };
    },
    created() {
        const email = localStorage.getItem("userID");
        this.fetchUserInfo(email); // 컴포넌트 생성 시 기존 데이터를 불러오는 메소드 호출
    },
    methods: {
        convertAgeRange(age) {
            if (age === "14~19" || age === "14-19") {
                return "10";
            } else if (age === "20~29" || age === "20-29") {
                return "20";
            } else if (age === "30~39" || age === "30-39") {
                return "30";
            } else if (age === "40~49" || age === "40-49") {
                return "40";
            } else if (age === "50~59" || age === "50-59") {
                return "50";
            } else {
                return age;
            }
        },
        async fetchUserInfo(email) {
            try {
                const response = await axios.get("mypage/getUserInfo", {
                    params: { userEmail: email },
                });

                this.originalData = {
                    ...response.data,
                    //age: response.data.age, // age 프로퍼티에 값을 할당
                    ageRange: this.convertAgeRange(response.data.age), // 나이대 변환
                };

                this.updatedFields = { ...this.originalData }; // 수정할 정보를 updatedFields에 복사
                console.log(this.updatedFields);
            } catch (error) {
                console.error("기존 회원정보 불러오기 오류:", error);
            }
        },
        async updateUserInfo() {
            // console.log("!");
            const email = localStorage.getItem("userID");
            alert("수정이 완료되었습니다");

            // POST 요청을 보내기 위한 데이터 구성
            const updatedFields = {
                email: email,
                updatedFields: {
                    add1: this.updatedFields.add1,
                    add2: this.updatedFields.add2,
                    zipcode: this.updatedFields.zipcode,
                    phone_num: this.updatedFields.phone_num,
                    password: this.updatedFields.password,
                },
            };

            const requestData = {
                email: email,
                updatedFields: updatedFields,
            };

            try {
                const response = await axios.post("mypage/updateUserInfo", requestData);
                this.message = response.data.message;
                this.fetchUserInfo(localStorage.getItem("userID"));
                alert("수정되었습니다.");
            } catch (error) {
                console.error("회원정보 수정 오류:", error);
                this.message = "회원정보 수정에 실패했습니다.";
            }
        },
        convertGender(genderCode) {
            if (genderCode === "f") {
                return "여자";
            } else if (genderCode === "m") {
                return "남자";
            }
        },
        openAddressSearch() {
            new daum.Postcode({
                oncomplete: (data) => {
                    if (data.userSelectedType === "R" || data.userSelectedType === "J") {
                        // 우편번호와 주소 정보를 업데이트
                        this.updatedFields.zipcode = data.zonecode;
                        this.updatedFields.add1 = data.roadAddress || data.jibunAddress;
                        this.updatedFields.add2 = ""; // 상세주소 초기화
                        console.log(this.updatedFields);

                        // 상세주소 필드로 포커스 이동
                        document.getElementById("add2").focus();
                    }
                },
            }).open();
        },
        cancelUpdate() {
            // 수정 취소 시 원래 데이터로 초기화
            this.updatedFields = { ...this.originalData };
            this.message = ""; // 메시지 초기화
            window.location.href = "/mypage";
        },

        openPasswordModal() {
            this.passwordModal = true;
        },
        closePasswordModal() {
            this.passwordModal = false;
            this.newPassword = "";
            this.confirmPassword = "";
            this.isPasswordModalClosed = true;
        },
        checkNewPassword() {
            const validatePassword = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

            if (this.newPassword === "") {
                this.passwordValidationMessage = "";
            } else if (!validatePassword.test(this.newPassword)) {
                this.passwordValidationMessage = "비밀번호는 최소 8자리 이상 영문, 숫자, 특수문자가 각각 1개 이상 포함되어야 합니다.";
            } else {
                this.passwordValidationMessage = "";
            }
        },

        async changePassword() {
            console.log("?");
            if (this.newPassword === this.confirmPassword) {
                try {
                    const email = localStorage.getItem("userID");

                    const requestData = {
                        email: email,
                        updatedFields: {
                            password: this.newPassword,
                        },
                    };

                    const response = await axios.post("/mypage/updateUserPw", requestData);
                    this.message = response.data;

                    this.closePasswordModal();

                    alert("비밀번호가 변경되었습니다.");

                    //this.originalData.password = this.newPassword;

                    // 모달을 닫는 함수 호출
                    //this.closePasswordModal();

                    // newPassword와 confirmPassword 초기화
                    this.newPassword = "";
                    this.confirmPassword = "";
                } catch (error) {
                    console.error("비밀번호 변경 오류:", error);
                }
            } else {
                console.error("비밀번호가 일치하지 않습니다.");
            }
        },
        formatPhoneNumber() {
            // 숫자 이외의 문자 제거
            this.updatedFields.phone_num = this.updatedFields.phone_num.replace(/[^\d-]/g, "");
            // 하이픈(-) 삽입
            if (this.updatedFields.phone_num.length > 2) {
                this.updatedFields.phone_num = this.updatedFields.phone_num.replace(/^01([0|1|6|7|8|9])-?(\d{4})-?(\d{4})$/, "01$1-$2-$3");
            }
        },
    },
};
</script>
<style scoped></style>
