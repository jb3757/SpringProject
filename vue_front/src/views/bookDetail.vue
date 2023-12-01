<template>
    <GnbBar ref="gnbBarComponent" />
    <div class="detail_top"></div>
    <div class="title_wri_sum">
        <div class="detail_title">
            {{ bookDetailData.BOOK_TITLE }}
        </div>
        <div class="writer_date">{{ bookDetailData.BOOK_AUTHOR }} | {{ bookDetailData.BOOK_PUBDATE }}</div>
        <div class="book_summary">
            {{ bookDetailData.BOOK_DESCRIPTION ? formatHtmlEntities(bookDetailData.BOOK_DESCRIPTION) : "" }}
        </div>
    </div>
    <div class="img_price_btn">
        <div class="detail_img">
            <img :src="bookDetailData.BOOK_COVER" alt="detail_img_sample" />
        </div>
        <div class="img_right">
            <div class="det_top_star">
                <star :rating="convertRatingToHalfStars(averageRating)" />
                <div class="top_star_score">
                    {{ averageRating }}
                    <div class="top_star_count">({{ reviewCount }})</div>
                </div>
            </div>
            <div class="price_point_set_1">
                <div class="price_point_title_1">판매가</div>
                <div class="price_point_detail_1">
                    {{ formatCurrency(bookDetailData.BOOK_PRICE) }}
                    <div class="won">원</div>
                </div>
            </div>
            <div class="price_point_set_2">
                <div class="price_point_title_2">포인트</div>
                <div class="price_point_detail_2">{{ bookDetailData.BOOK_POINT }}P</div>
            </div>
            <div class="price_point_set_3">
                <div class="price_point_title_3">배송비</div>
                <div class="price_point_detail_3">
                    2,500
                    <div class="won_3">원</div>
                </div>
            </div>
            <!-- --------------------------------------------------------------------------------------------- -->
            <!-- 갯수 늘리고 카운트, 가격 -->
            <div class="count_price">
                <div class="count_btn_ib">
                    <div class="count_btn">
                        <button class="count_minus" @click="decreaseCount">
                            <img src="@/assets/img/count_minus.png" alt="count_minus" />
                        </button>
                        <div class="count_num">{{ countNum }}</div>
                        <button class="count_plus" @click="increaseCount">
                            <img src="@/assets/img/count_plus.png" alt="count_plus" />
                        </button>
                    </div>
                </div>
                <div class="payment_price">{{ formatCurrency(paymentPrice) }}</div>
                <div class="won_4">원</div>
            </div>
            <div id="likeButton" class="pay_cart_like">
                <button class="pay_btn" @click="gotoPay">결제하러 가기</button>
                <button class="go_to_cart">
                    <img @click="addToCart(bookDetailData.BOOK_ID)" src="@/assets/img/detail_cart.png" alt="detail_cart" />
                </button>
                <button class="detail_like_btn" @click="toggleLike">
                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="20" vie wBox="0 0 22 20">
                        <path
                            d="M21.6071 6.60738C21.6071 13.5969 11.7775 19.2544 11.3589 19.488C11.2486 19.5506 11.1252 19.5834 11 19.5834C10.8747 19.5834 10.7514 19.5506 10.641 19.488C10.2224 19.2544 0.392822 13.5969 0.392822 6.60738C0.394577 4.96608 1.01378 3.39252 2.11457 2.23194C3.21537 1.07136 4.70787 0.418537 6.26463 0.416687C8.22033 0.416687 9.93262 1.30335 11 2.8021C12.0673 1.30335 13.7796 0.416687 15.7353 0.416687C17.2921 0.418537 18.7846 1.07136 19.8854 2.23194C20.9862 3.39252 21.6054 4.96608 21.6071 6.60738Z"
                            :fill="isLiked ? '#4E4EFF' : '#7d7d7d'"
                        />
                        <path
                            d="M16 0C13.9 0 12.074 1.00731 11 2.69539C9.926 1.00731 8.1 0 6 0C4.40927 0.0019706 2.88422 0.675006 1.75941 1.87146C0.634589 3.06792 0.00185261 4.6901 0 6.38214C0 9.48812 1.82 12.7207 5.41 15.9883C7.05505 17.4794 8.83201 18.7974 10.716 19.924C10.8033 19.9739 10.9009 20 11 20C11.0991 20 11.1967 19.9739 11.284 19.924C13.168 18.7974 14.945 17.4794 16.59 15.9883C20.18 12.7207 22 9.48812 22 6.38214C21.9981 4.6901 21.3654 3.06792 20.2406 1.87146C19.1158 0.675006 17.5907 0.0019706 16 0ZM11 18.6263C9.359 17.619 1.2 12.309 1.2 6.38214C1.20132 5.02845 1.70746 3.73061 2.60735 2.77341C3.50724 1.81621 4.72737 1.27784 6 1.27643C8.028 1.27643 9.731 2.4284 10.445 4.28348C10.4902 4.40053 10.5671 4.50065 10.6659 4.57111C10.7647 4.64157 10.881 4.67919 11 4.67919C11.119 4.67919 11.2353 4.64157 11.3341 4.57111C11.4329 4.50065 11.5098 4.40053 11.555 4.28348C12.269 2.4284 13.972 1.27643 16 1.27643C17.2726 1.27784 18.4928 1.81621 19.3927 2.77341C20.2925 3.73061 20.7987 5.02845 20.8 6.38214C20.8 12.309 12.641 17.619 11 18.6263Z"
                            fill="white"
                        />
                    </svg>
                </button>
            </div>
        </div>
    </div>

    <hr class="detail_main_next_hr" />

    <div class="detail_review_set">
        <div class="rev_top">
            <div class="review_con_title">
                책 리뷰 (
                {{ reviewCount }}
                )
            </div>
            <button class="go_to_review" @click="toggleBtn">리뷰 작성</button>
        </div>
        <!-- 댓글 작성-------------------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------- -->
        <div class="write_rev" id="write_review" v-if="!showBtn">
            <div class="write_star">
                <fieldset class="rating">
                    <input type="radio" id="star5" name="rating" value="5" /><label class="full" for="star5"></label>
                    <input type="radio" id="star4half" name="rating" value="4 and a half" /><label class="half" for="star4half"></label>
                    <input type="radio" id="star4" name="rating" value="4" /><label class="full" for="star4"></label>
                    <input type="radio" id="star3half" name="rating" value="3 and a half" /><label class="half" for="star3half"></label>
                    <input type="radio" id="star3" name="rating" value="3" /><label class="full" for="star3"></label>
                    <input type="radio" id="star2half" name="rating" /><label class="half" for="star2half"></label>
                    <input type="radio" id="star2" name="rating" value="2" /><label class="full" for="star2"></label>
                    <input type="radio" id="star1half" name="rating" value="1 and a half" /><label class="half" for="star1half"></label>
                    <input type="radio" id="star1" name="rating" value="1" /><label class="full" for="star1"></label>
                    <input type="radio" id="starhalf" name="rating" value="half" /><label class="half" for="starhalf"></label>
                </fieldset>
            </div>
            <div class="write_rev_under_set">
                <textarea class="write_text" placeholder="댓글을 입력하세요" ref="reviewComment" />
                <button type="button" class="rev_send" @click="sendReview">
                    <svg xmlns="http://www.w3.org/2000/svg" width="22" height="23" viewBox="0 0 22 23" fill="none">
                        <path d="M1 1.74097V9.74097L13.5 11.241L1 12.741V21.241L20.5 11.241L1 1.74097Z" fill="#6969FF" stroke="#6969FF" />
                    </svg>
                </button>
            </div>
        </div>

        <!-- 댓글 작성-------------------------------------------------------------------------------------------------------
    --------------------------------------------------------------------------------------------------------------------- -->
        <br />
        <div class="total_rec">
            <div class="total_rec_title">구매자 총점</div>
            <div class="total_star_rec">
                <star :rating="convertRatingToHalfStars(averageRating)" />
                <div class="total_rec_num">
                    <!-- {{ formattedAverageRating }} -->
                    <span class="total_rec_num_10">{{ averageRating }}/10</span>
                </div>
            </div>
        </div>
        <hr class="review_title_next_hr" />
        <div class="title_fillter">
            <div class="rev_sec_title">구매 리뷰</div>
            <select v-model="sortvalue" @change="fetchReviewData" name="items1" class="review_filltering">
                <option :value="'최신순'">최신순</option>
                <option :value="'평점 높은 순'">평점 높은 순</option>
                <option :value="'평점 낮은 순'">평점 낮은 순</option>
            </select>
        </div>
        <div class="review_con">
            <div v-if="reviewData.length === 0" class="no_data_review">리뷰가 없습니다.</div>
            <div v-else>
                <div v-for="review in reviewData" :key="review.REVIEW_ID" class="single_review">
                    <div class="single_rev_top">
                        <div class="review_created_at_flex">
                            <div class="single_review_writer">{{ review.review_writer }}</div>
                            <div class="single_review_rating_star"><star :rating="convertRatingToHalfStars(review.REV_RATING)" /></div>
                            <div class="single_review_rating_num">{{ review.REV_RATING }}</div>
                        </div>
                        <div class="review_created_at">{{ review.REV_CREATED_AT }}</div>
                    </div>
                    <div class="review_comment">{{ review.REV_COMMENT }}</div>
                </div>
            </div>
        </div>
    </div>
    <div class="exc_return_set">
        <div class="exc_return_top">
            <div class="review_con_title">교환/반품/품절 안내</div>
            <RouterLink to="/qnaWrite" class="go_to_review">1:1 문의</RouterLink>
        </div>
        <hr class="review_title_next_hr" />
        <div class="exc_return_con_title">반품/교환 방법</div>
        <div class="exc_return_con_text">
            마이룸 > 주문관리 > 주문/배송내역 > 주문조회 > 반품/교환 신청, [1:1 상담 > 반품/교환/환불] 또는 고객센터 (1544-1900)<br />* 오픈마켓,
            해외배송 주문, 기프트 주문시 [1:1 상담>반품/교환/환불] 또는 고객센터 (1544-1900)
        </div>
        <hr />
        <div class="exc_return_con_title">반품/교환가능 기간</div>
        <div class="exc_return_con_text">변심반품의 경우 수령 후 7일 이내,<br />상품의 결함 및 계약내용과 다를 경우 문제점 발견 후 30일 이내</div>
        <hr />
        <div class="exc_return_con_title">반품/교환비용</div>
        <div class="exc_return_con_text">변심 혹은 구매착오로 인한 반품/교환은 반송료 고객 부담</div>
        <hr />
        <div class="exc_return_con_title">반품/교환 불가 사유</div>
        <div class="exc_return_con_text">
            1) 소비자의 책임 있는 사유로 상품 등이 손실 또는 훼손된 경우<br />(단지 확인을 위한 포장 훼손은 제외) <br />2) 소비자의 사용, 포장 개봉에
            의해 상품 등의 가치가 현저히 감소한 경우 <br />예) 화장품, 식품, 가전제품(악세서리 포함) 등 <br />3) 복제가 가능한 상품 등의 포장을 훼손한
            경우 <br />예) 음반/DVD/비디오, 소프트웨어, 만화책, 잡지, 영상 화보집 <br />4) 소비자의 요청에 따라 개별적으로 주문 제작되는 상품의 경우
            ((1)해외주문도서) <br />5) 디지털 컨텐츠인 eBook, 오디오북 등을 1회 이상 다운로드를 받았을 경우 <br />6) 시간의 경과에 의해 재판매가
            곤란한 정도로 가치가 현저히 감소한 경우 <br />7) 전자상거래 등에서의 소비자보호에 관한 법률이 정하는 소비자 청약철회 제한 내용에 해당되는
            경우
        </div>
    </div>
    <!-- 플로팅-->
    <Floating />
    <!-- 스크롤 TOP -->
    <ScrollTop />

</template>

<script>
import axios, { Axios } from "axios";
import "../assets/css/bookDetail.css";
import GnbBar from "../components/gnbBar.vue";
import star from "@/components/star.vue";

import Floating from "../components/floating.vue";
import ScrollTop from "@/components/scrollTop.vue";


export default {
    components: {
        GnbBar,
        star, 
        Floating, 
        ScrollTop
    },
    data() {
        return {
            bookDetailData: [],
            reviewData: [],
            reviewCount: 0,
            isLiked: false,
            sortvalue: "최신순",
            showBtn: true,
            countNum: 1,
            bookId: this.$route.params.bookNum,
            averageRating: 0,

            // 댓글 데이터를 페이지별로 나눈 배열
        };
    },
    created() {
        this.email = localStorage.getItem("userID");
    },
    mounted() {
        this.bookDetail();
        this.fetchReviewData();
        this.fetchReviewCount();
        this.fetchAverageRating();
        this.setRecentview(); //최근 본 책 db에 삽입
        // this.averageRating();
        // this.reviewAverageData();
    },
    computed: {
        paymentPrice() {
            return this.countNum * this.bookDetailData.BOOK_PRICE;
        },
        // formattedAverageRating() {
        //     return this.reviewAverageData ? this.reviewAverageData.toFixed(1) : "0.0";
        // },
        formattedBookDetailData() {
            if (this.bookDetailData) {
                return this.formatHtmlEntities(this.bookDetailData.description);
            }
            return "";
        },
    },
    methods: {
        toggleBtn() {
            this.showBtn = !this.showBtn; // showBtn 데이터 값을 토글
        },
        // 최신순 리스트 불러오기-------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------
        bookDetail() {
            axios({
                url: "http://localhost:3000/detail/",
                method: "get",
                params: {
                    bookNum: this.bookId,
                },
            })
                .then((response) => {
                    console.log(response.data); // 가져온 데이터 확인
                    this.bookDetailData = response.data[0];
                })
                .catch((error) => {
                    console.error("책 상세 정보 가져오기 오류:", error);
                });
        },
        formatHtmlEntities(input) {
            return input.replace(/&lt;/g, "< ").replace(/&gt;/g, " > ");
        },
        async addToCart() {
            if (localStorage.getItem("userID")) {
                await axios({
                    url: "http://localhost:3000/detail/gotoCart",
                    method: "POST",
                    data: {
                        email: this.email,
                        bookId: this.bookId,
                    },
                }).then((res) => {
                    alert("장바구니에 추가되었습니다.", this.bookId);
                    this.$refs.gnbBarComponent.getCartNum();
                });
            } else {
                const conResult = confirm("로그인이 필요합니다. \n 로그인 하시겠습니까?");
                conResult ? (window.location.href = "/login") : null;
            }
        },

        //좋아요 추가/취소
        toggleLike() {
            if (localStorage.getItem("userID")) {
                const data = {
                    bookId: this.bookId,
                };

                axios
                    .post(`http://localhost:3000/floating/cancellikebooks?userEmail=${localStorage.getItem("userID")}`, data)
                    .then(() => {
                        this.isLiked = !this.isLiked;
                        // 좋아요 토글 이후에 리뷰 개수 및 평균 평점 업데이트
                        this.fetchReviewData();
                        this.fetchReviewCount();
                        this.fetchAverageRating();
                    })
                    .catch((error) => {
                        console.error("좋아요 에러", error);
                    });
            } else {
                const result = confirm("로그인이 필요합니다. \n 로그인하시겠습니까?");
                if (result) {
                    window.location.href = "/login";
                }
            }
        },

        // ... (
        // 리뷰 시작-------------------------------------------------------------------------------------------------------
        async sendReview() {
            const ratingInput = document.querySelector("input[name='rating']:checked");
            const rating = ratingInput ? parseFloat(ratingInput.value) : 0; // Get selected rating value

            const comment = this.$refs.reviewComment.value; // Get the review comment from the textarea

            if (!rating || !comment) {
                alert("리뷰 내용과 별점 추가해주세요");
                return;
            }

            const reviewData = {
                REV_WRITER: localStorage.getItem("userID"), // Assuming you store the user's ID in localStorage
                REV_ORDERITEM_BOOK: this.bookId,
                REV_COMMENT: comment,
                REV_RATING: rating * 2,
            };

            try {
                const response = await axios.post("http://localhost:3000/review/postreview", reviewData);

                // Reset the review form
                this.$refs.reviewComment.value = "";
                ratingInput.checked = false;

                // Fetch updated review data and average rating
                this.fetchReviewData();
                this.fetchReviewCount();
                this.fetchAverageRating();

                alert(response.data.message); // Display success message
            } catch (error) {
                console.error("리뷰 전송 실패:", error);
                alert("리뷰 전송 실패.");
            }
        },
        //리뷰 개수 가져오기
        fetchReviewCount() {
            axios({
                url: `http://localhost:3000/review/reviewcount/${this.bookId}`,
                method: "get",
            })
                .then((response) => {
                    this.reviewCount = response.data.reviewCount;
                })
                .catch((error) => {
                    console.error("Counting Err", Error);
                });
        },

        //리뷰 평균 별점
        async fetchAverageRating() {
            try {
                const res = await axios.get(`http://localhost:3000/review/averagerating/${this.bookId}`);
                this.averageRating = res.data.averageRating;
                console.log(this.averageRating);
            } catch (err) {
                console.error("average Err", err);
            }
        },

        // 리뷰 데이터 최신순 리스트 불러오기-------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------

        fetchReviewData() {
            if (this.sortvalue === "최신순") {
                this.loadRecentReviews();
            } else if (this.sortvalue === "평점 높은 순") {
                this.loadTopRatedReviews();
            } else if (this.sortvalue === "평점 낮은 순") {
                this.loadLowRatedReviews();
            }
        },
        // 리뷰 데이터 최신순 리스트 불러오기-------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------
        loadRecentReviews() {
            axios({
                url: "http://localhost:3000/review/reviewdata",
                method: "get",
                params: {
                    bookId: this.bookId,
                },
            })
                .then((response) => {
                    // 서버 응답 처리
                    this.reviewData = response.data.review; // reviewData에 데이터 저장
                })
                .catch((error) => {
                    console.error("Error fetching review data:", error);
                });
        },
        // 리뷰 데이터 평점 높은 순 리스트 불러오기-------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------
        loadTopRatedReviews() {
            axios({
                url: "http://localhost:3000/review/reviewrating",
                method: "get",
                params: {
                    bookId: this.bookId,
                },
            })
                .then((response) => {
                    // 서버 응답 처리
                    this.reviewData = response.data.review; // reviewData에 데이터 저장
                })
                .catch((error) => {
                    console.error("Error fetching review data:", error);
                });
        },
        // 리뷰 데이터 평점 낮은 순 리스트 불러오기-------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------
        loadLowRatedReviews() {
            axios({
                url: "http://localhost:3000/review/lowreviewrating",
                method: "get",
                params: {
                    bookId: this.bookId,
                },
            })
                .then((response) => {
                    // 서버 응답 처리
                    this.reviewData = response.data.review; // reviewData에 데이터 저장
                })
                .catch((error) => {
                    console.error("Error fetching review data:", error);
                });
        },
        // 리뷰 데이터 평점 낮은 순 리스트 불러오기-------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------
        // 리뷰 -------------------------------------------------------------------------------------------------------

        // averageRating() {
        //     axios({
        //         url: `http://localhost:3000/review/averagerating/${this.bookId}`,
        //         method: "get",
        //     })
        //         .then((response) => {
        //             this.reviewAverageData = response.data.averageRating;
        //         })
        //         .catch((error) => {
        //             console.error("평균 평점을 불러오는 중 오류 발생:", error);
        //         });
        // },
        convertRatingToHalfStars(number) {
            number = number / 2;
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
        decreaseCount() {
            if (this.countNum > 1) {
                this.countNum--; // 현재 카운트 값이 1보다 큰 경우에만 1 감소
            }
        },
        increaseCount() {
            this.countNum++; // countNum 값을 1 증가
        },
        updateCartNum() {
            this.$refs.gnbBarComponent.getCartNum();
        },
        formatReviewScore(number) {
            if (Number.isInteger(number)) {
                return number.toFixed(1); // 정수인 경우 소수점 자리를 1로 설정하여 변환
            } else {
                return number.toString(); // 소수인 경우 그대로 문자열로 변환
            }
        },
        //최근본 책 데이터 삽입
        //------------------------
        setRecentview() {
            const useremail = localStorage.getItem("userID");
            const bookid = this.bookId;

            axios({
                url: "http://localhost:3000/detail/setRec",
                method: "POST",
                data: {
                    email: useremail,
                    bookid: bookid,
                },
            })
                .then((res) => {
                    console.log(res.data);
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        //결제
        //----------------------
        gotoPay() {
            const bookPrice = this.bookDetailData.BOOK_PRICE;
            const bookfee = bookPrice >= 50000 ? 0 : 2500;
            const totalPoint = bookPrice * 0.05;
            console.log(this.$route.params.id);
            console.log(this.bookDetailData.BOOK_PRICE);
            console.log(bookfee);
            console.log(totalPoint);
            console.log(this.bookDetailData.BOOK_TITLE);
            console.log(localStorage.getItem("userID"));

            axios({
                url: "http://localhost:3000/detail/gotoPay",
                method: "POST",
                data: {
                    bookId: this.bookId,
                    bookNum: 1,
                    email: localStorage.getItem("userID"),
                    total_pay: bookPrice,
                    total_point: totalPoint,
                    fee: bookfee,
                },
            })
                .then((res) => {
                    console.log(res.data);
                    this.$router.push({ name: "paymentPage", query: { payid: res.data } });
                })
                .catch((error) => {
                    console.error("Error goToPay :", error);
                });
        },
        formatCurrency(value) {
            if (typeof value !== "number") {
                return "";
            }
            return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
    },
};
</script>
