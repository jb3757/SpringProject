<style src="../assets/css/survey.css"></style>

<template>
    <GnbBar />
    <div class="survey">
        <!-- 대분류 -->
        <div v-if="currentPage == 1" class="survey_container">
            <div class="survey_container_header">
                <div class="survey_state_dots">
                    <span v-for="i in 2" :key="i" :class="['survey_state_dot', { survey_selected: i === currentPage }]"></span>
                </div>
                <h2 class="state_title">추천 받고싶은 대분류를 선택하세요 (최대 5개)</h2>
            </div>
            <div class="survey_container_main">
                <button @click="selectKeyword" class="survey_select_btn" v-for="(item, i) in category" :key="item.id">{{ item.top }}</button>
            </div>
            <div class="survey_container_footer">
                <button @click="nextPage" class="survey_next_btn">다음</button>
            </div>
        </div>
        <!-- 소분류 -->
        <div v-if="currentPage == 2" class="survey_container">
            <div class="survey_container_header">
                <div class="survey_state_dots">
                    <span v-for="i in 2" :key="i" :class="['survey_state_dot', { survey_selected: i === currentPage }]"></span>
                </div>
                <h2 class="state_title">추천 받고싶은 키워드를 선택하세요 (최대 5개)</h2>
                <div class="state_myselect_keyword">
                    <span class="survey_myselect_btn" v-for="(item, i) in selectedMainKeywords" :key="i">{{ item }}</span>
                </div>
            </div>
            <div class="survey_container_main">
                <button @click="selectSubKeyword" class="survey_select_btn" v-for="(item, i) in selectedMiddleKeywords" :key="i">{{ item }}</button>
            </div>
            <div class="survey_container_footer">
                <button @click="prevPage" class="survey_next_btn">이전</button>
                <button @click="sendSelectedKeyword" class="survey_next_btn">선택 완료</button>
            </div>
        </div>
    </div>
</template>

<script>
import GnbBar from "../components/gnbBar.vue";

import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

export default {
    name: "",
    components: { GnbBar },
    data() {
        return {
            // 유저 이메일
            email: "",

            currentPage: 1, // 1: 대분류 리스트 출력 2: 소분류 리스트 출력
            selectedCount: 0,
            selectedMainKeywords: [], //선택한 대분류 키워드
            selectedMiddleKeywords: [], //선택한 대분류들의 소분류 키워드
            selectedKeyword: [], //선택한 소분류 키워드
            category: [
                { id: 1, top: "경제경영", middle: ["경제학/경제일반", "기업 경영", "재테크/투자"], expanded: false },
                { id: 2, top: "과학", middle: ["기초과학/교양과학", "의학", "화학", "공학"], expanded: false },
                { id: 3, top: "사회과학", middle: ["비평/칼럼", "기업 사회문제", " 정치학/외교학/행정학"], expanded: false },
                { id: 4, top: "소설/시/희곡", middle: ["한국소설", "일본소설", "영미소설", "추리/미스터리소설", "과학소설(SF)", "호러/공포소설", " 액션/스릴러소설", "로맨스소설", "시", "희곡"], expanded: false },
                { id: 5, top: "수험서/자격증", middle: ["국가기술자격", "국가전문자격", " 공무원 수험서", "한국사능력검정시험", "공인중개사/주택관리사", "운전면허", "컴퓨터 활용능력", "편입/검정고시/독학사"], expanded: false },
                { id: 6, top: "에세이", middle: ["한국에세이", "외국에세이", "여행에세이", "사랑/연애 에세이", "자연에세이"], expanded: false },
                { id: 7, top: "여행", middle: ["서울/수도권 여행가이드", "국내 여행가이드", "캠핑여행", "유럽여행", "일본여행", "중국여행", "동남아시아여행", "세계일주여행"], expanded: false },
                { id: 8, top: "역사", middle: ["동양사 일반", "역사학", "한국사 일반", "한국고대~고려시대", "조선사", "한국근현대사", "아시아사", "세계사 일반"], expanded: false },
                { id: 9, top: "예술/대중문화", middle: ["예술/대중문화의 이해", "미술", "음악", "영화/드라마", "사진", "건축", "뮤지컬/오페라"], expanded: false },
                { id: 10, top: "외국어", middle: ["어학사전", "토익", "토플", "영어회화", "영문법", "영어사전", "일본어", "중국어", "한자", " 통역/번역"], expanded: false },
                { id: 11, top: "요리/살림", middle: ["건강요리", "다이어트 요리", "도시락", "디저트", "살림/정리수납", "술/음료/차", "이유식", "제과제빵"], expanded: false },
                { id: 12, top: "인문학", middle: ["교양 인문학", "철학 일반", "서양철학", "동양철학", " 심리학/정신분석학", "인류학/고고학"], expanded: false },
                { id: 13, top: "자기계발", middle: ["성공", "리더십", "인간관계", "창의적사고/두뇌계발", "취업/진로/유망직업", "20대의자기계발"], expanded: false },
                { id: 14, top: "장르소설", middle: ["추리/미스터리소설", " 판타지/환상문학", "호러/공포", "액션/스릴러", "로맨스"], expanded: false },
                { id: 15, top: "컴퓨터/모바일", middle: ["웹디자인/홈페이지", "인공지능", "프로그래밍 개발/방법론", "프로그래밍 언어", "PC/게임/디지털 카메라"], expanded: false },
            ],
        };
    },

    setup() {},
    created() {},
    mounted() {
        this.email = localStorage.getItem("userID");
    },
    unmounted() {},
    methods: {
        selectKeyword(event) {
            // 대분류 버튼의 선택 상태를 관리
            const buttonElement = event.target;
            const buttonText = buttonElement.innerText; //클릭한 버튼의 텍스트를 가져와서 buttonText 변수에 저장

            if (buttonElement.classList.contains("survey_selected")) {
                // 선택 취소 로직
                buttonElement.classList.remove("survey_selected");
                const keywordIndex = this.selectedMainKeywords.indexOf(buttonText);
                if (keywordIndex !== -1) {
                    this.selectedMainKeywords.splice(keywordIndex, 1);
                }
                this.selectedCount--;
            } else if (this.selectedCount < 5) {
                // 선택 로직
                buttonElement.classList.add("survey_selected");
                this.selectedMainKeywords.push(buttonText);
                this.selectedCount++;
            }
        },
        selectSubKeyword(event) {
            // 소분류 버튼의 선택 상태를 관리
            const buttonElement = event.target;
            const buttonText = buttonElement.innerText; //클릭한 버튼의 텍스트를 가져와서 buttonText 변수에 저장

            if (buttonElement.classList.contains("survey_selected")) {
                // 선택 취소 로직
                buttonElement.classList.remove("survey_selected");
                const keywordIndex = this.selectedKeyword.indexOf(buttonText);
                if (keywordIndex !== -1) {
                    this.selectedKeyword.splice(keywordIndex, 1);
                }
            } else if (this.selectedKeyword.length < 5) {
                // 선택 로직
                buttonElement.classList.add("survey_selected");
                this.selectedKeyword.push(buttonText);
            }
        },
        getSelectedMiddleKeywords() {
            // 선택한 대분류에 해당하는 소분류 키워드를 selectedMiddleKeywords 배열에 업데이트
            //flatMap()은 배열의 각 요소에 대해 주어진 함수를 실행하고, 그 함수의 결과를 모두 하나의 배열로 합침
            this.selectedMiddleKeywords = this.selectedMainKeywords.flatMap((mainKeyword) => {
                const selectedCategory = this.category.find((item) => item.top === mainKeyword);
                if (selectedCategory) {
                    return selectedCategory.middle;
                }
                return [];
            });
        },
        prevPage() {
            this.currentPage = 1;
            this.selectedMainKeywords = [];
        },
        nextPage() {
            if (this.currentPage === 1) {
                this.getSelectedMiddleKeywords();
            }

            this.currentPage = 2;
            this.selectedCount = 0;
        },
        async sendSelectedKeyword() {
            await axios({
                url: "http://localhost:3000/sur/insert",
                method: "POST",
                data: {
                    SUR_USER_EMAIL: this.email,
                    SUR_LIKE_1: this.selectedKeyword[0],
                    SUR_LIKE_2: this.selectedKeyword[1],
                    SUR_LIKE_3: this.selectedKeyword[2],
                    SUR_LIKE_4: this.selectedKeyword[3],
                    SUR_LIKE_5: this.selectedKeyword[4],
                },
            })
                .then(async (res) => {
                    if (res.data == "저장 성공") {
                        alert("키워드가 저장되었습니다.");
                    }
                    location.replace("/");
                })
                .catch((error) => {
                    alert(error);
                });
        },
    },
};
</script>
