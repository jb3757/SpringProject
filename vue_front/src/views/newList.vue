<style src="../assets/css/newList.css"></style>

<template>
    <GnbBar ref="childComponent" />
    <div class="new">
        <div class="new_header">
            <h2>신상품</h2>
        </div>
        <div class="new_items">
            <div v-for="(item, i) in displayedPosts" :key="i" class="new_item">
                <div class="new_item_img">
                    <a :href="`/detail/${item.BOOK_ID}`"><img :src="item.BOOK_COVER" alt="" /></a>
                </div>
                <div class="new_item_info">
                    <h2 class="best_item_info_title">
                        <a :href="`/detail/${item.BOOK_ID}`">{{ item.BOOK_TITLE }}</a>
                    </h2>
                    <span class="new_item_info_author">{{ item.BOOK_AUTHOR }} | {{ item.BOOK_PUBDATE }}</span>
                    <div class="new_item_info_price">
                        <span>{{ formatNumber(item.BOOK_PRICE) }}</span>
                        <span>원</span>
                    </div>
                    <p class="best_item_info_summary">
                        {{ item.BOOK_DESCRIPTION }}
                    </p>
                    <div class="new_item_info_star">
                        <span v-if="item.reviewpoint == 0" class="best_item_info_star_num">0.0</span>
                        <span v-else class="best_item_info_star_num">{{ formatReviewScore(item.reviewpoint) }}</span>
                        <StarIcon :rating="convertRatingToHalfStars(reviewScore[i])" />
                    </div>
                </div>
                <div class="new_item_order">
                    <a @click="goToPay(item.BOOK_ID, item.BOOK_PRICE)" href="#">결제하러 가기</a>
                    <p><span>내일({{ month }}/{{ nextDay }})</span> 도착예정</p>
                </div>
                <div class="new_item_btn">
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
            <button
                v-for="pageNumber in pageNumbers"
                :key="pageNumber"
                @click="changePage(pageNumber)"
                :class="{ active: pageNumber === currentPage }"
            >
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
import Floating from "../components/floating.vue";
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
            newListData: [],
            reviewScore: [], // 리뷰 점수를 얻어온다고 가정
            email: "",
            bookId: "",

            //좋아요 여부
            isLiked: false,
            likeBook: [],

            // 페이징
            perPage: 10, //한 페이지에 보여줄 게시물 수
            currentPage: 1, // 현재 페이지 번호
            totalPages: 1, // 총 페이지 수
            maxDisplayedPages: 9, // 표시할 최대 페이지 수 (현재 페이지를 중심으로 좌우로 표시)

            nextDay: 0,
            month: 0
        };
    },

    setup() { },
    created() {
        this.email = localStorage.getItem("userID");
        this.getLikeBook();
        this.getNewList();
        this.getNextDate();
    },
    mounted() { },
    unmounted() { },
    computed: {
        totalPages() {
            // 전체 페이지 수 계산
            this.totalPages = Math.ceil(this.newListData.length / this.perPage);
            return Math.ceil(this.newListData.length / this.perPage);
        },
        pageNumbers() {
            const start = Math.max(1, this.currentPage - Math.floor(this.maxDisplayedPages / 2));
            const end = Math.min(this.totalPages, start + this.maxDisplayedPages - 1);
            return Array.from({ length: end - start + 1 }, (_, i) => start + i);
        },
    },
    methods: {
        changePage(pageNumber) {
            if (pageNumber === "first") {
                // 맨 앞 페이지로 이동
                pageNumber = 1;
            } else if (pageNumber === "last") {
                // 맨 뒤 페이지로 이동
                pageNumber = this.totalPages;
            } else {
                pageNumber = parseInt(pageNumber); // 페이지 번호로 변환
            }

            window.scrollTo({ top: 0, behavior: "auto" });
            if (pageNumber >= 1 && pageNumber <= this.totalPages) {
                this.currentPage = pageNumber;
                // 페이지 변경 시 추가 로직 수행
                // 예: API 호출하여 해당 페이지 데이터 가져오기
                // displayedPosts 업데이트
                this.displayedPosts = this.newListData.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
            }
        },
        async getNewList() {
            await axios({
                url: "http://localhost:3000/booklist/getNew",
                method: "POST",
                data: {},
            })
                .then((res) => {
                    if (localStorage.getItem("userID")) {
                        this.newListData = res.data.map((book) => {
                            return {
                                ...book,
                                isLiked: this.likeBook.includes(book.BOOK_ID), // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                            };
                        });
                        for (var i in res.data) {
                            //별이 5개이므로 총점10점을 2로 나눔
                            this.reviewScore.push(res.data[i].reviewpoint / 2);
                        }
                    } else {
                        this.newListData = res.data.map((book) => {
                            return {
                                ...book,
                                isLiked: false, // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                            };
                        });
                        for (var i in res.data) {
                            this.newListData.push(res.data[i]);
                            //별이 5개이므로 총점10점을 2로 나눔
                            this.reviewScore.push(res.data[i].reviewpoint / 2);
                        }
                    }
                    // displayedPosts에 categoryData 데이터 복사
                    this.displayedPosts = this.newListData.slice((this.currentPage - 1) * this.perPage, this.currentPage * this.perPage);
                })
                .catch((err) => {
                    alert(err);
                });
        },
        //회원이 좋아요한 책 ID 가져오기
        async getLikeBook() {
            await axios({
                url: "http://localhost:3000/bookList/checkLikeList",
                method: "POST",
                data: {
                    email: this.email,
                },
            }).then((res) => {
                this.likeBook = res.data;
            });
        },
        async likeToggle(bookId) {
            if (localStorage.getItem("userID")) {
                this.bookId = bookId;
                await axios({
                    url: "http://localhost:3000/detail/likeOrDislike",
                    method: "POST",
                    data: {
                        email: this.email,
                        bookId: this.bookId,
                    },
                }).then((res) => {
                    console.log(res.data, "좋아요 추가삭제");
                    this.getLikeBook();
                    this.getNewList();
                });
            } else {
                const conResult = confirm("로그인이 필요합니다. \n 로그인 하시겠습니까?");
                conResult ? (window.location.href = "/login") : null;
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
                        bookId: this.bookId,
                    },
                }).then((res) => {
                    alert("장바구니에 추가되었습니다.");
                    this.$refs.childComponent.getCartNum();
                });
            } else {
                const conResult = confirm("로그인이 필요합니다. \n 로그인 하시겠습니까?");
                conResult ? (window.location.href = "/login") : null;
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
                    fee: bookfee,
                },
            })
                .then((res) => {
                    console.log(res.data.payID);
                    this.$router.push({ name: "paymentPage", query: { payid: res.data.payID } });
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
