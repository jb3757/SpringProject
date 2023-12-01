<style src="../assets/css/floating.css"></style>

<template>
    <!-- 최근 본 -->
    <div @click="openFloating" class="floating_btn">
        <img src="../assets/img/floating.png" alt="" />
        <span class="floating_num">{{ formatNumber(floatingRecent.length) }}</span>
    </div>
    <div ref="float" class="floating_modal_wrap display_none">
        <div class="floating_modal">
            <div class="floating_modal_header">
                <h2 ref="floatRecent" class="floating_modal_heart_btn current"
                    @click="floatingMenuRecent(), getFloatRecentBook()">
                    최근 본
                </h2>
                <h2 ref="floatHeart" class="floating_modal_recent_btn" @click="floatingMenuHeart(), getFloatHeartBook()">
                    찜 목록
                </h2>
                <span @click="closeFloating" class="cursor">✖</span>
            </div>
            <!-- 최근 본 -->
            <div v-if="floatingState == 'recent'" class="floating_modal_recent_wrap">
                <div class="floating_modal_list_header">
                    <div class="floating_modal_list_header_left">
                        <span>{{ floatingRecent.length }}</span>
                        <span>건</span>
                    </div>
                    <div @click="allDeleteFloatRecentBook" class="floating_modal_list_header_right cursor">
                        <img src="../assets/img/trash.png" alt="" />
                        <span>전체삭제</span>
                    </div>
                </div>
                <div class="floating_modal_list">
                    <div v-for="(item, i) in floatingRecent" class="floating_modal_item">
                        <a class="floating_modal_item_img" :href="`/detail/${item.BOOK_ID}`"><img :src="item.BOOK_COVER"
                                alt="" /></a>
                        <div class="floating_modal_item_info">
                            <a :href="`/detail/${item.BOOK_ID}`">
                                <h2>{{ item.BOOK_TITLE }}</h2>
                            </a>
                            <p>{{ item.BOOK_AUTHOR }}</p>
                            <div class="floating_modal_item_info_price">
                                <span>10%</span>
                                <span>{{ formatNumber(item.BOOK_PRICE) }}</span>원
                            </div>
                        </div>
                        <div class="floating_modal_item_control">
                            <img v-if="item.isLiked == true" @click="likeToggle(item.BOOK_ID)"
                                src="../assets/img/heartFill.png" alt="Heart Filled" />
                            <img v-else-if="item.isLiked == false" @click="likeToggle(item.BOOK_ID)"
                                src="../assets/img/heart.png" alt="Heart" />
                            <span @click="deleteFloatRecentBook(item.BOOK_ID)" class="cursor">✖</span>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 찜 목록 -->
            <div v-if="floatingState == 'heart'" class="floating_modal_heart_wrap">
                <div class="floating_modal_list_header">
                    <div class="floating_modal_list_header_left">
                        <span>{{ floatingHeart.length }}</span>
                        <span>건</span>
                    </div>
                    <div @click="allDeleteFloatHeartBook" class="floating_modal_list_header_right cursor">
                        <img src="../assets/img/trash.png" alt="" />
                        <span>전체삭제</span>
                    </div>
                </div>
                <div class="floating_modal_list">
                    <div v-for="(item, i) in floatingHeart" class="floating_modal_item">
                        <a class="floating_modal_item_img" :href="`/detail/${item.BOOK_ID}`"><img :src="item.BOOK_COVER"
                                alt="" /></a>
                        <div class="floating_modal_item_info">
                            <a :href="`/detail/${item.BOOK_ID}`">
                                <h2>{{ item.BOOK_TITLE }}</h2>
                            </a>
                            <p>{{ item.BOOK_AUTHOR }}</p>
                            <div class="floating_modal_item_info_price">
                                <span>10%</span>
                                <span>{{ formatNumber(item.BOOK_PRICE) }}</span>원
                            </div>
                        </div>
                        <div class="floating_modal_item_control">
                            <img @click="likeToggle(item.BOOK_ID)"
                                src="../assets/img/heartFill.png" alt="Heart Filled" />
                            <!-- <img v-if="item.isLiked == true" @click="likeToggle(item.BOOK_ID)"
                                src="../assets/img/heartFill.png" alt="Heart Filled" />
                            <img v-else-if="item.isLiked == false" @click="likeToggle(item.BOOK_ID)"
                                src="../assets/img/heart.png" alt="Heart" /> -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

export default {
    name: '',
    components: {},
    data() {
        return {
            // 플로팅 데이터
            floatingState: "recent",
            floatingRecent: [],
            floatingHeart: [],
            email: "",

            //좋아요 여부
            isLiked: false,
            likeBook: [],
        };
    },

    setup() { },
    created() {
        this.email = localStorage.getItem("userID");
        this.getLikeBook();
    },
    mounted() {
        this.getFloatRecentBook();

    },
    unmounted() { },
    methods: {
        openFloating() {
            const floatdiv = this.$refs.float.classList;
            floatdiv.toggle("display_none");
            
        },
        closeFloating() {
            const floatdiv = this.$refs.float.classList;
            floatdiv.toggle("display_none");
        },
        floatingMenuRecent() {
            const floatRecentBtn = this.$refs.floatRecent;
            const floatHeartBtn = this.$refs.floatHeart;
            this.floatingState = "recent";
            if (this.floatingState == "recent") {
                floatRecentBtn.classList.add("current");
                floatHeartBtn.classList.remove("current");
            }
        },
        floatingMenuHeart() {
            const floatRecentBtn = this.$refs.floatRecent;
            const floatHeartBtn = this.$refs.floatHeart;
            this.floatingState = "heart";
            if (this.floatingState == "heart") {
                floatHeartBtn.classList.add("current");
                floatRecentBtn.classList.remove("current");
            }
        },
        //최근 본 목록 조회
        async getFloatRecentBook() { //해결
            this.floatingRecent = [];
            await axios({
                url: "http://localhost:3000/floating/recentbook",
                method: "GET",
                params: {
                    userEmail: this.email
                },
            })
                .then((res) => {
                    if (localStorage.getItem("userID")) {
                        console.log(res.data);
                        console.log( res.data.recentBooks);
                        this.floatingRecent = res.data.map(book => {
                            return {
                                ...book,
                                isLiked: this.likeBook.includes(book.BOOK_ID) // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                            };
                        });
                    } else {
                        this.floatingRecent = res.data.map(book => {
                            return {
                                ...book,
                                isLiked: false // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                            };
                        });
                    }
                })
                .catch((err) => {
                    alert(err);
                });
        },
        // 최근 본 목록 삭제
        async deleteFloatRecentBook(bookId) {
            await axios({
                url: `http://localhost:3000/floating/delrecentbook/${bookId}`,
                method: "DELETE",
                params: {
                    id: bookId
                },
            })
                .then((res) => {
                    this.getFloatRecentBook()
                })
                .catch((err) => {
                    alert(err);
                });
        },
        //최근 본 전체 삭제
        async allDeleteFloatRecentBook() {
            await axios({
                url: "http://localhost:3000/floating/delAllRec",
                method: "DELETE",
                params: {
                    userEmail: this.email
                }
            })
                .then((res) => {
                    this.getFloatRecentBook();
                })
                .catch((err) => {
                    alert(err);
                });
        },
        //찜 목록 조회
        async getFloatHeartBook() {
            this.floatingHeart = [];
            await axios({
                url: "http://localhost:3000/floating/likebook",
                method: "GET",
                params: {
                    userEmail: this.email
                },
            })
                .then((res) => {
                    console.log(res, "찜하기")
                    if (localStorage.getItem("userID")) {
                        this.floatingHeart = res.data.likeBooks.map(book => {
                            return {
                                ...book,
                                isLiked: this.likeBook.includes(book.BOOK_ID) // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                            };
                        });
                    } else {
                        this.floatingHeart = res.data.likeBooks.map(book => {
                            return {
                                ...book,
                                isLiked: false // 해당 책의 BOOK_ID가 likeBook 배열에 포함되어 있는지 확인하여 isLiked 값을 설정
                            };
                        });
                    }
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        //찜목록 전체 삭제
        async allDeleteFloatHeartBook() {
            await axios({
                url: "http://localhost:3000/floating/delAllLike",
                method: "DELETE",
                params: {
                    userEmail: this.email
                }
            })
                .then((res) => {
                    this.getFloatHeartBook();
                })
                .catch((err) => {
                    alert(err);
                });
        },
        //회원이 좋아요한 책 ID 가져오기
        async getLikeBook() {
            await axios({
                url: "http://localhost:3000/floating/checkLikeList",
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
                        this.getFloatRecentBook();
                        this.getFloatHeartBook();
                    })

            } else {
                const conResult = confirm("로그인이 필요합니다. \n 로그인 하시겠습니까?");
                conResult ? window.location.href = "/login" : null;
            }

        },

        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
    }
}
</script>