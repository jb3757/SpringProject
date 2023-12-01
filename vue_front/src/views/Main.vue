<style src="../assets/css/Main.css"></style>

<template>
    <GnbBar />

    <div ref="content" class="main content">
        <!-- 이미지슬라이더 -->
        <div class="image-slider">
            <div class="slider-wrapper" v-if="slidesReady">
                <div
                    class="slider"
                    :style="{
                        transform: `translateX(-${currentIndex * 100}%)`,
                        transition: transitionDuration,
                    }"
                >
                    <div v-for="(image, index) in images" :key="index" class="slide">
                        <img :src="image" alt="Slide Image" />
                    </div>
                </div>
                <div class="controls">
                    <button @click="prevSlide" class="prev-btn">&lt;</button>
                    <button @click="nextSlide" class="next-btn">&gt;</button>
                </div>
                <div class="dots">
                    <span
                        v-for="(image, index) in images"
                        :key="index"
                        @click="goToSlide(index)"
                        :class="{ active: index === currentIndex }"
                        class="dot"
                    ></span>
                </div>
            </div>
        </div>

        <!-- 책추천-->
        <div v-if="logined == false" class="main_container_blur">
            <h2>로그인하시면 취향에 맞는 책을 보여드려요</h2>
            <a href="/login">로그인하고 책 추천받기</a>
        </div>
        <div class="main_container">
            <h2 class="recommend_title">
                <p v-if="logined == true">
                    <span class="recommend_username">{{ name }}</span> 님에게 추천하는 책이예요
                </p>
                <p v-else-if="logined == false"><span class="recommend_username">사용자</span> 님에게 추천하는 책이예요</p>
            </h2>
            <div ref="mainBlur" class="main_item_wrap blur">
                <!-- 로그인시 추천출력 -->
                <div v-if="logined == true" class="recommend_item" v-for="(item, i) in recBook" :key="i">
                    <a :href="`/detail/${item.BOOK_ID}`"><img class="main_item_img" :src="item.BOOK_COVER" alt="" /></a>
                    <div class="main_item_info">
                        <h5 class="main_item_title">
                            <a :href="`/detail/${item.BOOK_ID}`">{{ item.BOOK_TITLE }}</a>
                        </h5>
                        <span class="main_item_writer">{{ item.BOOK_AUTHOR }}</span>
                        •
                        <span class="main_item_publisher">{{ item.BOOK_PUBLISHER }}</span>
                    </div>
                </div>
                <!-- 비로그인시 베스트출력 -->
                <div v-else-if="logined == false" class="recommend_item" v-for="(item, i) in bestBook">
                    <img class="main_item_img" :src="item.BOOK_COVER" alt="" />
                    <div class="main_item_info">
                        <h5 class="main_item_title">{{ item.BOOK_TITLE }}</h5>
                        <span class="main_item_writer">{{ item.BOOK_AUTHOR }}</span>
                        •
                        <span class="main_item_publisher">{{ item.BOOK_PUBLISHER }}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 베스트추천-->
        <div class="main_container">
            <div class="main_contents_header">
                <h2 class="main_title">베스트</h2>
                <a href="/best">더보기 ＋</a>
            </div>
            <div class="main_item_wrap">
                <div class="main_contents_item" v-for="(item, i) in bestBook" :key="i">
                    <a :href="`/detail/${item.BOOK_ID}`"><img class="main_item_img2" :src="item.BOOK_COVER" alt="" /></a>
                    <div class="main_item_info">
                        <h5 class="main_item_title">
                            <a :href="`/detail/${item.BOOK_ID}`">{{ item.BOOK_TITLE }}</a>
                        </h5>
                        <span class="main_item_writer">{{ item.BOOK_AUTHOR }}</span>
                        •
                        <span class="main_item_publisher">{{ item.BOOK_PUBLISHER }}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 신상품추천-->
        <div class="main_container">
            <div class="main_contents_header">
                <h2 class="main_title">신상품</h2>
                <a href="/new">더보기 ＋</a>
            </div>
            <div class="main_item_wrap">
                <div class="main_contents_item" v-for="(item, i) in newestBook" :key="i">
                    <a :href="`/detail/${item.BOOK_ID}`"><img class="main_item_img2" :src="item.BOOK_COVER" alt="" /></a>
                    <div class="main_item_info">
                        <h5 class="main_item_title">
                            <a :href="`/detail/${item.BOOK_ID}`">{{ item.BOOK_TITLE }}</a>
                        </h5>
                        <span class="main_item_writer">{{ item.BOOK_AUTHOR }}</span>
                        •
                        <span class="main_item_publisher">{{ item.BOOK_PUBLISHER }}</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 이벤트배너-->
        <div class="main_event">
            <a href="event"> <img class="main_event_img" src="../assets/img/testimg.png" alt="" /> </a>
        </div>

        <!-- 굿즈-->
        <div class="main_container">
            <div class="main_contents_header">
                <h2 class="main_title">굿즈</h2>
                <a href="/goods">더보기 ＋</a>
            </div>
            <div class="main_item_wrap">
                <div class="recommend_item">
                    <img class="goods_img" src="../assets/img/mockup_tum.png" alt="mockup_book" />
                    <div class="goods_ball">텀블러</div>
                </div>
                <div class="recommend_item">
                    <img class="goods_img" src="../assets/img/mockup_book.png" alt="" />
                    <div class="goods_ball">노트</div>
                </div>
                <div class="recommend_item">
                    <img class="goods_img" src="../assets/img/mockup_eco.png" alt="" />
                    <div class="goods_ball">에코백</div>
                </div>
                <div class="recommend_item">
                    <img class="goods_img" src="../assets/img/mockup_bookmark.png" alt="" />
                    <div class="goods_ball">책갈피</div>
                </div>
            </div>
        </div>

        <!-- 플로팅-->
        <Floating />
    </div>

    <!-- toTop -->
    <div v-show="state.showTopButton" @click="scrollToTop" class="scroll_top_btn">
        <div>
            <span>TOP</span>
        </div>
    </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import GnbBar from "../components/gnbBar.vue";
import Floating from "../components/floating.vue";
import { ref, reactive, onMounted, onUnmounted } from "vue";
import { isLogindDisplay } from "../mixins/main.js";

export default {
    mixins: [isLogindDisplay],
    components: { GnbBar, Floating },
    data() {
        return {
            images: [require("../assets/img/book2.jpg"), require("../assets/img/book1.jpg"), require("../assets/img/book3.jpg")],
            email: "",
            name: "",
            // 이미지슬라이드
            currentIndex: 0,
            slidesReady: false, // 슬라이드 준비 여부 상태 추가
            transitionDuration: "0.3s", // 이미지 이동 애니메이션 시간
            //추천책 데이터
            recBook: [],
            bestBook: [],
            newestBook: [],
            // 플로팅 데이터
            floatingState: "recent",
        };
    },

    setup() {
        const state = reactive({
            showTopButton: false,
        });

        // 스크롤 이벤트 핸들러
        const handleScroll = () => {
            const scrollTop = window.scrollY;

            // 스크롤이 일정 위치 이상으로 내려갔을 때 Top 버튼 표시
            state.showTopButton = scrollTop >= 300;
        };

        // Top 버튼 클릭 시 스크롤을 위로 부드럽게 이동
        Math.easeInOutQuad = function (t, b, c, d) {
            t /= d / 2;
            if (t < 1) return (c / 2) * t * t + b;
            t--;
            return (-c / 2) * (t * (t - 2) - 1) + b;
        };

        const scrollToTop = () => {
            const startTime = performance.now();
            const duration = 200; //스크롤 속도
            const startTop = window.scrollY;

            const scrollStep = (timestamp) => {
                const currentTime = timestamp || performance.now();
                const elapsed = currentTime - startTime;
                const ease = Math.easeInOutQuad(elapsed, startTop, -startTop, duration);

                window.scrollTo(0, ease);

                if (elapsed < duration) {
                    window.requestAnimationFrame(scrollStep);
                }
            };
            window.requestAnimationFrame(scrollStep);
        };
        // 스크롤 이벤트 리스너 등록
        onMounted(() => {
            window.addEventListener("scroll", handleScroll);
        });
        // 컴포넌트 제거 시 스크롤 이벤트 리스너 해제
        onUnmounted(() => {
            window.removeEventListener("scroll", handleScroll);
        });
        return {
            state,
            scrollToTop,
        };
    },
    mounted() {
        this.email = localStorage.getItem("userID");
        this.name = localStorage.getItem("userNick");
        // this.checkUserSurvey();
        this.getRecBook();
        this.getBestBook();
        this.getNewestBook();
        // this.setViewer();
        // 이미지 슬라이드가 렌더링되고 준비가 완료되면 slidesReady를 true로 설정
        this.slidesReady = true;
    },
    methods: {
        // 이전 슬라이드로 이동하는 메서드 (이미지가 오른쪽으로 넘어가는 애니메이션 추가)
        prevSlide() {
            if (this.slidesReady) {
                if (this.currentIndex > 0) {
                    this.currentIndex--;
                } else {
                    this.currentIndex = this.images.length - 1;
                }
                this.transitionDuration = "0.3s";
            }
        },
        // 다음 슬라이드로 이동하는 메서드 (이미지가 왼쪽으로 넘어가는 애니메이션 추가)
        nextSlide() {
            if (this.slidesReady) {
                if (this.currentIndex < this.images.length - 1) {
                    this.currentIndex++;
                } else {
                    this.currentIndex = 0;
                }
                this.transitionDuration = "0.3s";
            }
        },
        goToSlide(index) {
            this.currentIndex = index;
            this.transitionDuration = "0.3s";
        },
        async getRecBook() {
            console.log("함수 실행");
            await axios({
                url: "http://localhost:3000/booklist/getRecList",
                method: "POST",
                data: {
                    email: this.email,
                },
            })
                .then((res) => {
                    console.log("함수 중간");
                    for (var i in res.data.slice(0, 8)) {
                        this.recBook.push(res.data[i]);
                    }
                    console.log(this.recBook);
                })
                .catch((err) => {
                    console.log("함수 에러");
                    alert(err);
                });
        },
        async getBestBook() {
            await axios({
                url: "http://localhost:3000/main/getBestBook",
                method: "POST",
                data: {},
            })
                .then((res) => {
                    for (var i in res.data) {
                        this.bestBook.push(res.data[i]);
                    }
                    console.log(this.bestBook);
                })
                .catch((err) => {
                    alert(err);
                });
        },
        async getNewestBook() {
            await axios({
                url: "http://localhost:3000/main/getNewestBook",
                method: "POST",
                data: {},
            })
                .then((res) => {
                    for (var i in res.data) {
                        this.newestBook.push(res.data[i]);
                    }
                    console.log(this.newestBook);
                })
                .catch((err) => {
                    alert(err);
                });
        },
        async getRecentBook() {
            await axios({
                url: "http://localhost:3000/floating/recentbook",
                method: "GET",
                data: {},
            })
                .then((res) => {
                    console.log(res);
                })
                .catch((err) => {
                    alert(err);
                });
        },
        async checkUserSurvey() {
            await axios({
                url: "http://localhost:3000/sur/check",
                method: "POST",
                data: {
                    email: this.email,
                },
            })
                .then((res) => {
                    console.log(res.data);
                    if (res.data == "설문조사 안한 유저") {
                        window.location.href = "/survey";
                    }
                })
                .catch((err) => {
                    alert(err);
                });
        },
        setViewer() {
            const today = new Date().toLocaleDateString();

            if (sessionStorage.getItem("visit") === localStorage.getItem("userID")) {
                console.log("이미 방문한 유저");
                return;
            }

            axios({
                url: "/visit",
                method: "POST",
                data: { today: today },
            })
                .then((res) => {
                    console.log(res.data);
                    sessionStorage.setItem("visit", localStorage.getItem("userID"));
                })
                .catch((err) => {
                    console.log(err);
                });
        },
    },
};
</script>
