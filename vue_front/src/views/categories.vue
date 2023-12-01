<style src="../assets/css/searchList.css"></style>

<template>
    <GnbBar ref="childComponent" @search-keyword="handleSearchKeyword" />
    <div>
        <h1 class="search_category_header">카테고리 - {{ currentHeaderCategory }}</h1>
    </div>
    <div class="search">
        <div class="search_header">
            <div class="search_header_left">
                <span>'{{ currentCategory }}'</span>에 대한
                <span>{{ categoryData.length }}</span>개의 결과
            </div>
            <div class="search_header_right">
                <select @change="getCateGoryData" v-model="filterValue">
                    <option value="판매순">판매순</option>
                    <option value="신상품순">신상품순</option>
                </select>
            </div>
        </div>
        <div class="search_items">
            <div v-for="(item, i) in displayedPosts" :key="i" class="search_item">
                <div class="search_item_img">
                    <a :href="`/detail/${item.BOOK_ID}`"><img :src="item.BOOK_COVER" alt="" /></a>
                </div>
                <div class="search_item_info">
                    <h2 class="best_item_info_title"><a :href="`/detail/${item.BOOK_ID}`">{{ item.BOOK_TITLE }}</a></h2>
                    <span class="search_item_info_author">{{ item.BOOK_AUTHOR }} | {{ item.BOOK_PUBDATE }}</span>
                    <div class="search_item_info_price">
                        <span>{{ formatNumber(item.BOOK_PRICE) }}</span>
                        <span>원</span>
                    </div>
                    <p class="best_item_info_summary">
                        {{ item.BOOK_DESCRIPTION }}
                    </p>
                    <div class="search_item_info_star">
                        <span v-if="item.reviewpoint == 0" class="best_item_info_star_num">0.0</span>
                        <span v-else class="best_item_info_star_num">{{ formatReviewScore(item.reviewpoint) }}</span>
                        <StarIcon :rating="convertRatingToHalfStars(reviewScore[i])" />
                    </div>
                </div>
                <div class="search_item_order">
                    <a @click="goToPay(item.BOOK_ID, item.BOOK_PRICE)" href="#">결제하러 가기</a>
                    <p><span>내일({{ month }}/{{ nextDay }})</span> 도착예정</p>
                </div>
                <div class="search_item_btn">
                    <img v-if="item.isLiked == true" @click="likeToggle(item.BOOK_ID)" src="../assets/img/heartFill.png"
                        alt="Heart Filled" />
                    <img v-else-if="item.isLiked == false" @click="likeToggle(item.BOOK_ID)" src="../assets/img/heart.png"
                        alt="Heart" />
                    <img @click="addToCart(item.BOOK_ID)" src="../assets/img/cart2.png" alt="" />
                </div>
            </div>
        </div>
        <div class="booklist_paging">
            <button @click="changePage('first')" :disabled="currentPage === 1">맨앞</button>
            <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">이전</button>
            <button v-for="pageNumber in pageNumbers" :key="pageNumber" @click="changePage(pageNumber)"
                :class="{ active: pageNumber === currentPage }">
                {{ pageNumber }}
            </button>
            <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">다음</button>
            <button @click="changePage('last')" :disabled="currentPage === totalPages">맨뒤</button>
        </div>
        <!-- 플로팅-->
        <Floating />
        <!-- 스크롤 TOP -->
        <ScrollTop />
    </div>
</template>

<script>
import GnbBar from "../components/gnbBar.vue";
import Floating from "../components/floating.vue"
import StarIcon from "../components/star.vue"; // 별점 아이콘 컴포넌트의 경로를 수정해주세요.
import ScrollTop from "@/components/scrollTop.vue";

import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Contents-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

export default {

    name: "",
    components: { GnbBar, StarIcon, Floating, ScrollTop },
    data() {
        return {
            categoryData: [], // 카테고리 게시글 데이터
            // 페이징
            perPage: 10, //한 페이지에 보여줄 게시물 수
            currentPage: 1, // 현재 페이지 번호
            totalPages: 1, // 총 페이지 수
            maxDisplayedPages: 9, // 표시할 최대 페이지 수 (현재 페이지를 중심으로 좌우로 표시)

            //현재 카테고리 올바른 출력 위한 배열
            categoryHeader: [
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
            currentHeaderCategory: "",
            reviewScore: [], // 리뷰 점수를 얻어온다고 가정
            email: "",
            bookId: "",
            filterValue: "판매순",

            currentCategory: "",

            //좋아요 여부
            isLiked: false,
            likeBook: [],

            nextDay: 0,
            month: 0

        };
    },

    setup() { },
    created() {
        this.email = localStorage.getItem("userID");
        this.getLikeBook();
        this.getNextDate();
    },
    mounted() {
        this.$route.params.category == '장르소설' ? this.currentCategory = '소설/시/희곡' : this.currentCategory = this.$route.params.category;
        this.isCategoryHeaderMatch();
        console.log(this.currentCategory);
        this.$nextTick(() => {
            this.getCateGoryData();
        });
    },
    unmounted() { },
    computed: {
        totalPages() { // 전체 페이지 수 계산
            this.totalPages = Math.ceil(this.categoryData.length / this.perPage);
            return Math.ceil(this.categoryData.length / this.perPage);
        },
        pageNumbers() {
            const start = Math.max(1, this.currentPage - Math.floor(this.maxDisplayedPages / 2));
            const end = Math.min(this.totalPages, start + this.maxDisplayedPages - 1);
            return Array.from({ length: end - start + 1 }, (_, i) => start + i);
        }
    },
    watch: {
        '$route'(to, from) {
            this.$route.params.category == '장르소설' ? this.currentCategory = '소설/시/희곡' : this.currentCategory = this.$route.params.category;
            this.getCateGoryData();
            this.isCategoryHeaderMatch();
        }
    },
    methods: {

        changePage(pageNumber) {
            if (pageNumber === 'first') { // 맨 앞 페이지로 이동
                pageNumber = 1;
            } else if (pageNumber === 'last') { // 맨 뒤 페이지로 이동
                pageNumber = this.totalPages;
            } else {
                pageNumber = parseInt(pageNumber); // 페이지 번호로 변환
            }

            window.scrollTo({ top: 0, behavior: 'auto' });
            if (pageNumber >= 1 && pageNumber <= this.totalPages) {
                this.currentPage = pageNumber;
                // 페이지 변경 시 추가 로직 수행
                // 예: API 호출하여 해당 페이지 데이터 가져오기
                // displayedPosts 업데이트
                this.displayedPosts = this.categoryData.slice(
                    (this.currentPage - 1) * this.perPage,
                    this.currentPage * this.perPage
                );
            }
        },
        isCategoryHeaderMatch() {
            if (!this.categoryHeader.some(category => category.top.includes(this.currentCategory))) {
                const foundCategory = this.categoryHeader.find(category => category.middle.includes(this.currentCategory));
                if (foundCategory) {
                    this.currentHeaderCategory = foundCategory.top + ' ➜ ' + this.currentCategory;
                } else {
                    this.currentHeaderCategory = this.currentCategory;
                }
            } else {
                this.currentHeaderCategory = this.currentCategory;
            }
        },
        handleSearchKeyword(keyword) {
            this.searchKeyword = keyword;
            this.getCateGoryData();
        },
        //카테고리 데이터 가져오기
        async getCateGoryData() {
            //판매순
            if (this.filterValue === "판매순") {
                await axios({
                    url: "http://localhost:3000/bookList/bestCate",
                    method: "POST",
                    data: {
                        category: this.currentCategory
                    }
                })
                    .then((res) => {
                        if (localStorage.getItem("userID")) {
                            console.log(res, this.currentCategory + "카테고리 데이터");
                            this.categoryData = res.data;

                            this.categoryData = res.data.map(book => {
                                return {
                                    ...book,
                                    isLiked: this.likeBook.includes(book.BOOK_ID) // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                                };
                            });
                            for (var i in res.data) {
                                //별이 5개이므로 총점10점을 2로 나눔
                                this.reviewScore.push((res.data[i].reviewpoint) / 2)
                            }
                        } else {
                            this.categoryData = res.data.map(book => {
                                return {
                                    ...book,
                                    isLiked: false // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                                };
                            });
                        }
                        console.log(this.categoryData, "최종 카테고리")
                        console.log(this.reviewScore, "리뷰")
                        // displayedPosts에 categoryData 데이터 복사
                        this.displayedPosts = this.categoryData.slice(
                            (this.currentPage - 1) * this.perPage,
                            this.currentPage * this.perPage
                        );
                    })
                    .catch((err) => {
                        console.log(err);
                    })
            } else if (this.filterValue === "신상품순") {
                await axios({
                    url: "http://localhost:3000/bookList/newCate",
                    method: "POST",
                    data: {
                        category: this.currentCategory
                    }
                })
                    .then((res) => {
                        if (localStorage.getItem("userID")) {
                            console.log(res, this.currentCategory + "카테고리 데이터");
                            this.categoryData = res.data;

                            this.categoryData = res.data.map(book => {
                                return {
                                    ...book,
                                    isLiked: this.likeBook.includes(book.BOOK_ID) // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                                };
                            });
                            for (var i in res.data) {
                                //별이 5개이므로 총점10점을 2로 나눔
                                this.reviewScore.push((res.data[i].reviewpoint) / 2)
                            }
                        } else {
                            this.categoryData = res.data.map(book => {
                                return {
                                    ...book,
                                    isLiked: false // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                                };
                            });
                        }
                        console.log(this.categoryData, "최종 카테고리")
                        console.log(this.reviewScore, "리뷰")
                        // displayedPosts에 categoryData 데이터 복사
                        this.displayedPosts = this.categoryData.slice(
                            (this.currentPage - 1) * this.perPage,
                            this.currentPage * this.perPage
                        );
                    })
                    .catch((err) => {
                        console.log(err);
                    })
            }

        },
        //회원이 좋아요한 책 ID 가져오기
        async getLikeBook() {
            await axios({
                url: "http://localhost:3000/bookList/checkLikeList",
                method: "POST",
                data: {
                    email: this.email
                }
            })
                .then((res) => {
                    this.likeBook = res.data;
                })
        },
        async likeToggle(bookId) {
            if (localStorage.getItem("userID")) {
                this.bookId = bookId;
                await axios({
                    url: "http://localhost:3000/detail/likeOrDislike",
                    method: "POST",
                    data: {
                        email: this.email,
                        bookId: this.bookId
                    }
                })
                    .then((res) => {
                        console.log(res.data, "좋아요 추가삭제")
                        this.getLikeBook();
                        this.getCateGoryData();
                    })

            } else {
                const conResult = confirm("로그인이 필요합니다. \n 로그인 하시겠습니까?");
                conResult ? window.location.href = "/login" : null;
            }

        },
        //입력된 숫자를 주어진 범위에 따라 적절한 별점으로 변환
        convertRatingToHalfStars(number) {
            if (Number.isInteger(number)) {
                if (number >= 1 && number <= 5) {
                    return number;
                } else {
                    return 0; // 범위를 벗어나는 경우
                }
            } else {
                const integerPart = Math.floor(number);
                const decimalPart = number - integerPart;

                if (decimalPart < 0.5) {
                    return integerPart;
                } else {
                    return integerPart + 0.5;
                }
            }
        },
        formatReviewScore(number) {
            if (number === undefined) {
                return 'N/A'; // 또는 다른 특별한 값으로 대체
            }

            if (Number.isInteger(number)) {
                return number.toFixed(1); // 정수인 경우 소수점 자리를 1로 설정하여 변환
            } else {
                return number.toString(); // 소수인 경우 그대로 문자열로 변환
            }
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
        //장바구니에 추가
        async addToCart(bookId) {
            if (localStorage.getItem("userID")) {
                this.bookId = bookId;
                await axios({
                    url: "http://localhost:3000/detail/gotoCart",
                    method: "POST",
                    data: {
                        email: this.email,
                        bookId: this.bookId
                    }
                })
                    .then((res) => {
                        alert("장바구니에 추가되었습니다.");
                        this.$refs.childComponent.getCartNum();
                    })
            } else {
                const conResult = confirm("로그인이 필요합니다. \n 로그인 하시겠습니까?");
                conResult ? window.location.href = "/login" : null;
            }
        },
        // 결제하기 버튼 클릭시 서버로 보낼 데이터
        async goToPay(bookId, bookPrice) {
            const totalPoint = bookPrice * 0.05;
            const bookfee = bookPrice >= 50000 ? 0 : 2500;
            await axios({
                url: "http://localhost:3000/detail/gotoPay",
                method: "POST",
                data: {
                    bookId: bookId,
                    bookNum: 1,
                    email: this.email,
                    total_pay: bookPrice,
                    total_point: totalPoint,
                    fee: bookfee
                },
            })
                .then((res) => {
                    console.log(res.data.payID);
                    window.location.href = `/pay/${res.data.payID}`;
                })
                .catch((error) => {
                    console.error("Error goToPay :", error);
                });
        },
        getNextDate() {
            //내일 배송일로 나오게 하는 함수
            const today = new Date();
            this.nextDay = today.getDate() + 1;
            this.month = today.getMonth() + 1;
        },
    },
};
</script>
