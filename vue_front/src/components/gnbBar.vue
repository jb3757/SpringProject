<style src="../assets/css/gnbbar.css"></style>
<style src="../assets/css/reset.css"></style>
<template>
    <div class="gnb">
        <div class="gnb_bar">
            <div v-if="logined == false" class="gnb_bar_service">
                <a href="/join">회원가입</a>
                <a href="/login">로그인</a>
                <a href="/고객센터">고객센터</a>
            </div>
            <div v-else-if="logined == true" class="gnb_bar_service">
                <a @click="logout" href="/">로그아웃</a>
                <a href="/qna">고객센터</a>
            </div>
        </div>
    </div>
    <!-- 상단고정 검색헤더 -->

    <div class="gnb_bar_header_wrap">
        <div class="gnb_bar_header">
            <div class="gnb_bar_header_logo">
                <a href="/"><img src="../assets/img/logolong4.png" alt="" /></a>
            </div>
            <div class="gnb_bar_header_search" ref="search_box">
                <input
                    class="gnb_bar_header_search_input"
                    type="text"
                    placeholder="검색어를 입력하세요"
                    @keyup.enter="sendSearchKeyword"
                    :value="keyword"
                    @input="rec_search"
                />
                <!-- 검색 결과 미리보기 창 모달 -->
                <div v-show="rec_show" class="rec_search_back" ref="search_result_box">
                    <div class="rec_search_area">
                        <div v-for="searchBook in book_title" class="search_results" @click="go_detail_page(searchBook)">
                            {{ searchBook.BOOK_TITLE }}
                        </div>
                    </div>
                </div>
                <img @click="sendSearchKeyword" class="gnb_bar_header_search_icon" src="../assets/img/search.png" alt="" />
            </div>
            <div class="gnb_bar_header_user">
                <a href="/admin" v-show="email === 'admin@admin.com'"><img class="gnb_bar_header_admin" src="../assets/img/admin.png" /></a>
                <a href="/cart"
                    ><img class="gnb_bar_header_cart" src="../assets/img/cart1.png" alt="" />
                    <span class="gnb_bar_header_cart_num">{{ cartNum }}</span>
                </a>
                <a href="/mypage"><img class="gnb_bar_header_userimg" src="../assets/img/user.png" alt="" /></a>
            </div>
        </div>
    </div>
    <!-- nav-->
    <div class="nav_wrap">
        <div class="nav">
            <div class="nav_btn_wrap">
                <button @click="navBtnToggle(event)" ref="nav_btn1" class="nav_btn">☰</button>
                <button @click="navBtnToggle(event)" ref="nav_btn2" class="nav_btn display_none">✖</button>
            </div>
            <ul class="nav_category">
                <a class="nav_category_link" href="/recommendlist">
                    <li>추천</li>
                </a>
                <a class="nav_category_link" href="/best">
                    <li>베스트</li>
                </a>
                <a class="nav_category_link" href="/new">
                    <li>신상품</li>
                </a>
                <a class="nav_category_link" href="/event">
                    <li>이벤트</li>
                </a>
                <a class="nav_category_link" href="/goods">
                    <li>굿즈</li>
                </a>
            </ul>
            <div class="nav_sub">
                <a class="nav_sub_link" @click="setTapR" href="/event">룰렛</a>
                •
                <a class="nav_sub_link" @click="setTapA" href="/event">출석체크</a>
            </div>
            <!-- 카테고리 모달 -->
            <div v-if="showCategoryModal" class="nav_category_modal">
                <h2>카테고리</h2>
                <div class="nav_modal_main_category_wrap">
                    <h3>도서 전체</h3>
                    <ul class="nav_modal_main_category">
                        <li v-for="(main, i) in category" :key="main.id" class="nav_modal_main_category_list_main_wrap">
                            <div class="nav_modal_main_category_list_wrap">
                                <div class="nav_modal_main_category_list">
                                    <!-- <a href="/categorys/:category">{{ main.top }}</a> -->
                                    <router-link @click="closeCategoryModal" :to="`/categories/${encodeURIComponent(main.top)}`">{{
                                        main.top
                                    }}</router-link>
                                </div>
                                <div class="nav_modal_category_btn">
                                    <span @click="showCategoryMiddleBtn($event, main.id)">{{ main.expanded ? "―" : "＋" }}</span>
                                </div>
                            </div>
                            <ul v-if="main.expanded" class="nav_modal_middle_category_list">
                                <li v-for="(middle, index) in main.middle" :key="index">
                                    <router-link @click="closeCategoryModal" :to="`/categories/${encodeURIComponent(middle)}`">{{
                                        middle
                                    }}</router-link>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <!-- 카카오 로그인--------------------------------------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------------------------------------------------ -->
    <!-- <div v-else-if="provider === 'kakao'" class="gnb_bar_user_login">
        <div
          class="kakao_img"
          :style="{ 'background-image': 'url(' ＋ image ＋ ')' }"
          @click="toggleButtons"
        ></div>
        <div class="gnbmypage">
          <div v-if="isButtonsVisible">
            <div v-for="button in buttonList" :key="button.tab" class="gnbmypage_btns">
              <button
                @click="selectTab(button.tab)"
                :class="{ 'active-button': activeTab === button.tab }"
              >
                {{ button.name }}
              </button>
            </div>
          </div>
        </div>
      </div> -->

    <!-- 네이버 로그인--------------------------------------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------------------------------------------------ -->
    <!-- <div v-else-if="provider === 'naver'" class="gnb_bar_user_login">
        <img
          class="naver_img"
          :src="image"
          alt="profileExample"
          @click="toggleButtons"
        />
        <div class="gnbmypage">
          <div v-if="isButtonsVisible">
            <div v-for="button in buttonList" :key="button.tab" class="gnbmypage_btns">
              <button
                @click="selectTab(button.tab)"
                :class="{ 'active-button': activeTab === button.tab }"
              >
                {{ button.name }}
              </button>
            </div>
          </div>
        </div>
      </div> -->
    <!-- 로컬로그인, 유저가 이미지 넣음--------------------------------------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------------------------------------------------ -->
    <!-- <div
        v-else-if="provider === 'local' && image !== 'default'"
        class="gnb_bar_user_login"
      >
        <div
          class="local_img"
          :style="{
            'background-image': `url('http://localhost:3000/downloadProfile/${email}/${image}')`,
          }"
          @click="toggleButtons"
        ></div>

        <div class="gnbmypage">
          <div v-if="isButtonsVisible">
            <div v-for="button in buttonList" :key="button.tab" class="gnbmypage_btns">
              <button @click="selectTab(button.tab)">{{ button.name }}</button>
            </div>
          </div>
        </div>
      </div> -->
    <!-- 로컬로그인, 이미지 없음--------------------------------------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------------------------------------------------ -->
    <!-- <div
        v-else-if="image === 'default' && this.email !== 'admin@admin.com'"
        class="gnb_bar_user_login"
      >
        <img
          class="naver_img"
          src="../assets/img/profileExample.png"
          alt="profileExample"
          @click="toggleButtons"
        />
        <div class="gnbmypage">
          <div v-if="isButtonsVisible">
            <div v-for="button in buttonList" :key="button.tab">
              <button
                @click="selectTab(button.tab)"
                :class="[
                  'gnb-button',
                  { 'active-button': activeTab === button.tab },
                  button.tab,
                ]"
              >
                {{ button.name }}
              </button>
            </div>
          </div>
        </div>
      </div> -->

    <!-- admin 계정 로그인 했을 시--------------------------------------------------------------------------------------------------------------------
        ------------------------------------------------------------------------------------------------------------------------ -->
    <!-- <div
        v-else-if="provider === 'local' && this.email === 'admin@admin.com'"
        class="gnb_bar_user_login"
      >
        <a href="/admin"><button class="adminbtn">ADMIN</button></a>
        <img
          class="local_img"
          src="../assets/img/profileExample.png"
          alt="profileExample"
          @click="toggleButtons"
        />
        <div class="gnbmypage">
          <div v-if="isButtonsVisible">
            <div v-for="button in buttonList" :key="button.tab" class="gnbmypage_btns">
              <button
                @click="selectTab(button.tab)"
                :class="[
                  'gnb-button',
                  { 'active-button': activeTab === button.tab },
                  button.tab,
                ]"
              >
                {{ button.name }}
              </button>
            </div>
          </div>
        </div>
      </div> -->
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import { app } from "../main";
import { isLogindDisplay } from "../mixins/gnbBar.js";

export default {
    mixins: [isLogindDisplay],
    data() {
        return {
            email: "",
            nick: "",
            image: "",
            provider: "",
            cartNum: "",
            buttonList: [
                { name: "마이페이지", tab: "myPage" },
                { name: "내 코스", tab: "myCourse" },
                { name: "최근에 본 코스", tab: "recentCourse" },
                { name: "좋아요 리스트", tab: "likeList" },
                { name: "내가 쓴 댓글", tab: "myComment" },
                { name: "로그아웃", tab: "logout" },
            ],
            isButtonsVisible: false,
            activeTab: "",
            showCategoryModal: false,
            showCategoryMiddle: false,
            category: [
                {
                    id: 1,
                    top: "경제경영",
                    middle: ["경제학/경제일반", "기업 경영", "재테크/투자"],
                    expanded: false,
                },
                {
                    id: 2,
                    top: "과학",
                    middle: ["기초과학/교양과학", "의학", "화학", "공학"],
                    expanded: false,
                },
                {
                    id: 3,
                    top: "사회과학",
                    middle: ["비평/칼럼", "기업 사회문제", " 정치학/외교학/행정학"],
                    expanded: false,
                },
                {
                    id: 4,
                    top: "소설/시/희곡",
                    middle: [
                        "한국소설",
                        "일본소설",
                        "영미소설",
                        "추리/미스터리소설",
                        "과학소설(SF)",
                        "호러,공포소설",
                        "액션/스릴러소설",
                        "로맨스소설",
                        "시",
                        "희곡",
                        "영미소설",
                        "영미소설",
                    ],
                    expanded: false,
                },
                {
                    id: 5,
                    top: "수험서/자격증",
                    middle: [
                        "국가기술자격",
                        "국가전문자격",
                        "공무원 수험서",
                        "한국사능력검정시험",
                        "공인중개사/주택관리사",
                        "운전면허",
                        "컴퓨터 활용능력",
                        "편입/검정고시/독학사",
                    ],
                    expanded: false,
                },
                {
                    id: 6,
                    top: "에세이",
                    middle: ["한국에세이", "외국에세이", "여행에세이", "사랑/연애 에세이", "자연에세이"],
                    expanded: false,
                },
                {
                    id: 7,
                    top: "여행",
                    middle: [
                        "서울/수도권 여행가이드",
                        "국내 여행가이드",
                        "캠핑여행",
                        "유럽여행",
                        "일본여행",
                        "중국여행",
                        "동남아시아여행",
                        "세계일주여행",
                    ],
                    expanded: false,
                },
                {
                    id: 8,
                    top: "역사",
                    middle: ["동양사 일반", "역사학", "한국사 일반", "한국고대~고려시대", "조선사", "한국근현대사", "아시아사", "세계사 일반"],
                    expanded: false,
                },
                {
                    id: 9,
                    top: "예술/대중문화",
                    middle: ["예술/대중문화의 이해", "미술", "음악", "영화/드라마", "사진", "건축", "뮤지컬/오페라"],
                    expanded: false,
                },
                {
                    id: 10,
                    top: "외국어",
                    middle: ["어학사전", "토익", "토플", "영어회화", "영문법", "영어사전", "일본어", "중국어", "한자", " 통역/번역"],
                    expanded: false,
                },
                {
                    id: 11,
                    top: "요리/살림",
                    middle: ["건강요리", "다이어트 요리", "도시락", "디저트", "살림/정리수납", "술/음료/차", "이유식", "제과제빵"],
                    expanded: false,
                },
                {
                    id: 12,
                    top: "인문학",
                    middle: ["교양 인문학", "철학 일반", "서양철학", "동양철학", " 심리학/정신분석학", "인류학/고고학"],
                    expanded: false,
                },
                {
                    id: 13,
                    top: "자기계발",
                    middle: ["성공", "리더십", "인간관계", "창의적사고/두뇌계발", "취업/진로/유망직업", "20대의자기계발"],
                    expanded: false,
                },
                {
                    id: 14,
                    top: "장르소설",
                    middle: ["추리/미스터리소설", " 판타지/환상문학", "호러/공포", "액션/스릴러", "로맨스"],
                    expanded: false,
                },
                {
                    id: 15,
                    top: "컴퓨터/모바일",
                    middle: ["웹디자인/홈페이지", "인공지능", "프로그래밍 개발/방법론", "프로그래밍 언어", "PC/게임/디지털 카메라"],
                    expanded: false,
                },
            ],
            rec_show: false,
            rec_text: "",
            book_title: [],
            searchKeyword: "",
            keyword: "",
        };
    },
    mounted() {
        (this.email = localStorage.getItem("userID")),
            (this.nick = localStorage.getItem("userNick")),
            (this.image = localStorage.getItem("userImage")),
            (this.provider = localStorage.getItem("userProvider"));
        this.getCartNum();
        window.addEventListener("click", this.onClick);
    },
    methods: {
        // 생략
        toggleButtons() {
            this.isButtonsVisible = !this.isButtonsVisible;
        },
        navBtnToggle(event) {
            const navBtn1 = this.$refs.nav_btn1;
            const navBtn2 = this.$refs.nav_btn2;

            // 모달 출력
            if (this.showCategoryModal == false) {
                this.showCategoryModal = true;
            } else {
                this.showCategoryModal = false;
            }
            navBtn1.classList.toggle("display_none");
            navBtn2.classList.toggle("display_none");
        },
        closeCategoryModal() {
            this.showCategoryModal = false;
            this.$refs.nav_btn2.classList.add("display_none");
            this.$refs.nav_btn1.classList.remove("display_none");
        },
        showCategoryMiddleBtn(event, mainId) {
            const target = event.target;
            const mainIndex = this.category.findIndex((item) => item.id === mainId);

            if (mainIndex !== -1) {
                this.category.forEach((item, index) => {
                    if (index !== mainIndex) {
                        item.expanded = false; // 다른 메뉴의 expanded 상태를 모두 false로 설정
                    }
                });

                this.category[mainIndex].expanded = !this.category[mainIndex].expanded;

                // 모든 메뉴의 텍스트를 "+"로 초기화
                this.category.forEach((item) => {
                    item.text = "＋";
                });

                target.innerText = this.category[mainIndex].expanded ? "―" : "＋";
            }
        },
        selectTab(tab) {
            this.activeTab = tab;
            if (tab === "logout") {
                this.logout();
            } else if (tab === "myPage") {
                window.location.href = "/mypage";
                sessionStorage.setItem("selectGnb", "myCourse");
            } else if (tab === "myCourse") {
                window.location.href = "/mypage";
                sessionStorage.setItem("selectGnb", "myCourse");
            } else if (tab === "myComment") {
                window.location.href = "/mypage";
                sessionStorage.setItem("selectGnb", "myComment");
            } else if (tab === "recentCourse") {
                window.location.href = "/mypage";
                sessionStorage.setItem("selectGnb", "recentCourse");
            } else if (tab === "likeList") {
                window.location.href = "/mypage";
                sessionStorage.setItem("selectGnb", "likeList");
            }
        },
        getCartNum() {
            if (!localStorage.getItem("userID")) {
                this.cartNum = 0;
                return;
            }
            axios({
                url: "/cart/countCart",
                method: "POST",
                data: { email: localStorage.getItem("userID") },
            })
                .then((res) => {
                    this.cartNum = res.data;
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        logout() {
            localStorage.removeItem("userID");
            localStorage.removeItem("userNick");
            localStorage.removeItem("userProvider");
            // localStorage.clear(); // localStorage의 모든 항목 제거
            window.location.href = "/"; // 메인
        },
        rec_search(e) {
            this.keyword = e.target.value;
            if (this.keyword.length == 0) {
                this.rec_show = false;
                return;
            }
            this.rec_show = true;

            axios({
                url: "/main/searchBest",
                method: "GET",
                params: { searchKeyword: this.keyword },
            })
                .then((res) => {
                    if (res.data.length == 0) {
                        return (this.book_title = [{ BOOK_TITLE: "검색 결과가 없습니다." }]);
                    }
                    this.book_title = res.data;
                    this.cutTitle();
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        cutTitle() {
            const max_title = 35;

            for (var i = 0; i < this.book_title.length; i++) {
                if (this.book_title[i].BOOK_TITLE.length > max_title) {
                    this.book_title[i].BOOK_TITLE = this.book_title[i].BOOK_TITLE.slice(0, max_title) + "...";
                } else {
                    this.book_title[i].BOOK_TITLE = this.book_title[i].BOOK_TITLE;
                }
            }
        },
        go_detail_page(book) {
            if (book.BOOK_TITLE == "검색 결과가 없습니다.") {
                return;
            }
            window.location.href = `/detail/${book.BOOK_ID}`;
        },
        sendSearchKeyword() {
            var specialRule = /[`~!@#$%^&*|\\\'\";:\/?]/gi;
            if (specialRule.test(this.keyword)) {
                alert("특수문자는 검색 불가입니다.");
                return;
            }
            this.$router.replace({ name: "SearchList", query: { searchKeyword: this.keyword } });
        },
        onClick(e) {
            if (e.target.parentNode !== this.$refs.search_result_box && e.target.parentNode !== this.$refs.search_box) {
                this.rec_show = false;
            }
        },
        setTapR() {
            sessionStorage.setItem("eventTap", "룰렛");
        },
        setTapA() {
            sessionStorage.setItem("eventTap", "출석체크");
        },
    },
    beforeUnmount() {
        window.removeEventListener("click", this.onClick);
    },
};
</script>
