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
                    </div>
                </a>
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">최근 본</div>
                        <div class="like_recent_point_cou_num">{{ recentCountData }}</div>
                    </div>
                </a>
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">포인트</div>
                        <div class="like_recent_point_cou_num">{{ getUserData.USER_POINT }}<span>p</span></div>
                    </div>
                </a>
                <a href="#" class="mypage_like">
                    <div class="like_recent_point_cou_text">
                        <div class="like_recent_point_cou_title">쿠폰</div>
                        <div class="like_recent_point_cou_num">{{ getCouCountData }}</div>
                    </div>
                </a>
            </div>
        </div>
        <div class="mypage_top_second_right">
            <div class="grade_info_wrap">
                <div class="grade_info_top">
                    <div class="next_grade">다음달 예정 등급 :&nbsp;</div>
                    <div v-if="check == true" class="next_grade_db">{{ nextGrade }}</div>
                    <div v-if="check == false" class="next_grade_db">{{ couponGrade }}</div>
                </div>
                <div class="grade_under">
                    <div v-if="couponGrade != '플래티넘'" class="grade_info_second">
                        <div class="next_grade_s">{{ nextGrade }}</div>
                        <div class="next_grade_t">&nbsp;등급 혜택을 받으려면?</div>
                    </div>
                    <div v-if="couponGrade != '플래티넘'" class="grade_info_under">
                        <div class="next_grade_u">추가 구매금액 :&nbsp;</div>
                        <div class="ext_grade_u2">{{ nextPayCost1 }} <span class="won">원</span></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import { Pagination, Navigation } from "vue3-carousel";

export default {
    name: "",
    components: {
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
            nextGrade: "",
            nextPay: "",
            check: true,
            reqP: 0,
            okGo: false,
            nextPayCost1: ''
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
        this.getGraUp();
        this.getNextGra();
        this.nextPayCost();
    },
    methods: {
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
        getGraInfo() {
            const userEmail = localStorage.getItem("userID");

            axios({
                url: `/mypage/coupongrade/${userEmail}`,
                method: "GET",
            }).then((res) => {
                console.log(res.data);
                this.couponGrade = res.data.userGrade;

                this.setNextGra();
            });
        },
        getGraUp() {
            const userEmail = localStorage.getItem("userID");

            axios({
                url: `/mypage/couponupgrade/${userEmail}`,
                method: "GET",
            }).then((res) => {
                console.log(res.data);
                this.nextPay = res.data.additionalPayment;
                return;
            });
        },
        setNextGra() {
            if (this.okGo) {
            }

            if (this.couponGrade === "프렌즈") {
                this.nextGrade = "실버";
                return;
            } else if (this.couponGrade === "실버") {
                this.nextGrade = "골드";
                return;
            } else if (this.couponGrade === "골드") {
                this.nextGrade = "플레티넘";
                return;
            } else {
                this.nextGrade = this.couponGrade;
                return;
            }
        },
        getNextGra() {
            const email = localStorage.getItem("userID");

            axios({
                url: "/mypage/getNextGra",
                method: "POST",
                data: { email: email },
            }).then((res) => {
                this.reqP = res.data[0].totalPayment;
                console.log(this.reqP);

                // if (this.nextPay > this.reqP) {
                //     this.check = false;
                // } else {
                //     this.check = true;
                // }
            });
        },
        nextPayCost() {
            let result;
            if (this.nextPay - this.reqP < 0) {
                this.check = true;
                this.nextPayCost1 = 0;
                return;

            } else {
                this.check = false;
                this.nextPayCost1 = this.nextPay - this.reqP;
                return result;
                return;
            }
        },
    },
    computed: {

    },
};
</script>
