<template>
    <div class="mypage_top">
        <div class="mypage_top_left">
            <div class="mypage_profile_name_email">
                <div class="mypage_profile">
                    <img src="../assets/img/user.png" alt="user" />
                </div>
                <div class="mypage_profile_right">
                    <div class="name_grade">
                        <div class="mypage_grade">{{ couponGrade }}</div>
                        <div class="mypage_name">{{ getUserData.USER_NAME }}<span>님</span></div>
                    </div>
                    <div class="mypage_email">{{ getUserData.USER_EMAIL }}</div>
                </div>
            </div>
            <div class="like_recent_point_cou">
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">찜</div>
                        <div class="like_recent_point_cou_num">{{ likeCountData }}</div>
                    </div></a
                >
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">최근 본</div>
                        <div class="like_recent_point_cou_num">{{ recentCountData }}</div>
                    </div></a
                >
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">포인트</div>
                        <div class="like_recent_point_cou_num">{{ formatNumber(getUserData.USER_POINT) }}<span>p</span></div>
                    </div></a
                >
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">쿠폰</div>
                        <div class="like_recent_point_cou_num">{{ formatNumber(getCouCountData) }}</div>
                    </div></a
                >
            </div>
        </div>
        <div class="mypage_top_right">
            <div class="mypage_slide_title">신간 추천</div>
            <carousel :items-to-show="4" class="mypage_slide_set">
                <template #addons>
                    <navigation />
                    <pagination />
                </template>
                <slide v-for="(item, index) in recListData.slice(0, 9)" :key="index" class="mypage_slide">
                    <div class="slide_content">
                        <!-- <div class="my_rec_slide_title">{{ item.BOOK_TITLE }}</div> -->
                        <div class="my_rec_slide_img"><img :src="item.BOOK_COVER" :alt="item.BOOK_TITLE" /></div>
                    </div>
                </slide>
            </carousel>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { Carousel, Slide, Pagination, Navigation } from "vue3-carousel";

export default {
    name: "",
    components: {
        Carousel,
        Slide,
        Pagination,
        Navigation,
    },
    data() {
        return {
            getUserData: {
                USER_NAME: "",
                USER_EMAIL: "",
                USER_POINT: "",
            },
            likeCountData: 0,
            recentCountData: 0,
            getCouCountData: 0,
            recListData: [],
            couponGrade: "",
        };
    },
    created() {
        this.email = localStorage.getItem("userID");
        this.getUser();
        this.getLikeCount();
        this.getRecentCount();
        this.getRecList();
        this.getCouCount();
        this.getGraInfo();
    },
    methods: {
        getGraInfo() {
            const userEmail = localStorage.getItem("userID");

            axios({
                url: `/mypage/coupongrade/${userEmail}`,
                method: "GET",
            }).then((res) => {
                console.log(res.data);
                this.couponGrade = res.data.userGrade;
            });
        },
        getUser() {
            axios({
                url: "http://localhost:3000/mypage/getuser",
                method: "post",
                data: {
                    userEmail: this.email,
                },
            })
                .then((response) => {
                    this.getUserData = response.data.user;
                })
                .catch((error) => {
                    console.error("user데이터 받아오기 오류:", error);
                });
        },
        async getLikeCount() {
            try {
                const response = await axios.get("http://localhost:3000/mypage/getlikecount", {
                    params: {
                        userEmail: this.email,
                    },
                });

                // console.log("서버 응답 데이터:", response.data.likecount);
                this.likeCountData = response.data.likecount; // 할당
            } catch (error) {
                console.error("userlikecount 데이터 받아오기 오류:", error);
            }
        },
        async getRecentCount() {
            try {
                const response = await axios.get("http://localhost:3000/mypage/recentcount", {
                    params: {
                        userEmail: this.email,
                    },
                });

                // console.log("서버 응답 데이터:", response.data.recentCount);
                this.recentCountData = response.data.recentCount; // 할당
            } catch (error) {
                console.error("userlikecount 데이터 받아오기 오류:", error);
            }
        },
        async getCouCount() {
            try {
                const response = await axios.get("http://localhost:3000/mypage/couponcount", {
                    params: {
                        userEmail: this.email,
                    },
                });
                this.getCouCountData = response.data.couponCount;
                // console.log(response.data.couponCount);
            } catch (error) {
                console.error("쿠폰카운트 데이터 받아오기 오류:", error);
            }
        },
        async getRecList() {
            if (localStorage.getItem("userID")) {
                try {
                    const response = await axios.post("http://localhost:3000/booklist/getRecList", {
                        email: this.email,
                    });

                    this.recListData = response.data;
                    // console.log(response.data);
                    // displayedPosts에 categoryData 데이터 복사
                } catch (error) {
                    alert(error);
                    console.error(error);
                }
            }
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
    },
};
</script>
