<template>
    <GnbBar />
    <myPage_top />
    <div class="mypage_menu">
        <myPageSide />
    </div>
    <div class="right_box">
        <h1 class="mypage_title_big">전체 주문내역</h1>
        <div class="filter-container">
            <div class="date-filter">
                <input type="date" v-model="startDate" />
                ~
                <input type="date" v-model="endDate" />
                <!-- <button class="orderinfo_btn" @click="fetchFilteredOrders">검색</button> -->
            </div>

            <div class="order-search">
                <label>주문 검색</label>
                <input class="mypage_book_search" type="text" v-model="orderKeyword" placeholder="책 이름 검색" />
                <button class="orderinfo_btn" @click="searchOrders">검색</button>
            </div>

            <div class="status-filter">
                <label>배송 상태별 조회</label>
                <select v-model="selectedStatus">
                    <option value="전체">전체</option>
                    <option value="배송준비">배송준비</option>
                    <option value="배송중">배송중</option>
                    <option value="배송완료">배송완료</option>
                    <option value="주문취소">주문취소</option>
                </select>
                <button class="orderinfo_btn" @click="fetchOrdersByStatus">조회</button>
            </div>
        </div>

        <div class="order-list">
            <div v-for="order in filteredOrders" :key="order.ORDER_ID" class="order-box">
                <!-- 왼쪽에 배송 상태 및 결제일 -->
                <div class="order_status">
                    <div
                        :class="{
                            mypage_completed_order: order.ORDER_STATE === '배송완료',
                            mypage_delivery: order.ORDER_STATE === '배송중',
                            mypage_delivery_cancle: order.ORDER_STATE === '주문취소',
                        }"
                        class="mypage_order_state"
                        @click="
                            setSelectedStatus(order.ORDER_STATE);
                            fetchOrdersByStatus();
                        "
                    >
                        {{ order.ORDER_STATE }}
                    </div>
                    <div class="book-cover">
                        <img :src="order.items[0].BOOK_COVER" alt="Book Cover" />
                    </div>
                </div>

                <!-- 오른쪽에 책 정보 -->
                <div class="book_info">
                    <div class="order-date-right">{{ order.ORDER_PAYDATE }} 구매</div>
                    <p class="mypage_book_title">
                        {{ order.items[0].BOOK_TITLE }}
                        <span class="mypage_ordercnt" v-if="order.ORDER_CNT > 1"
                            ><span class="order_cnt_out"> 외</span> {{ order.ORDER_CNT - 1 }} 건</span
                        >
                    </p>
                    <p class="mypage_orderpay">{{ formatCurrency(order.ORDER_PAY) }}원</p>
                    <p>주문 상세</p>

                    <button
                        v-if="order.ORDER_STATE === '배송완료' && !isBuyConfirmed(order) && !order.items[0].BUY_CHECK"
                        class="confirm-button"
                        @click="showConfirmationAlert(order.ORDER_ID)"
                    >
                        구매확정
                    </button>
                    <button
                        v-else-if="order.ORDER_STATE === '배송완료' && (isBuyConfirmed(order) || order.items[0].BUY_CHECK)"
                        class="confirm-button-complete"
                        :disabled="order.items[0].BUY_CHECK"
                    >
                        구매확정
                    </button>
                    <!-- 구매확정 버튼 -->
                </div>
            </div>
        </div>
    </div>
    <!-- 플로팅-->
    <Floating />
    <!-- 스크롤 TOP -->
    <ScrollTop />

</template>
<script>
import axios from "axios";
import "@/assets/css/myPage.css";
import GnbBar from "../components/gnbBar.vue";
import myPage_top from "../components/myPage_top.vue";
import myPageSide from "../components/myPageSide.vue";
import Floating from "../components/floating.vue";
import ScrollTop from "@/components/scrollTop.vue";



export default {
    name: "",
    components: { GnbBar, myPage_top, myPageSide, Floating, ScrollTop },
    data() {
        return {
            startDate: null,
            endDate: null,
            orderKeyword: "",
            selectedStatus: "전체",
            orderList: [],
            orderState: "",
            buyConfirmed: false,
        };
    },
    created() {
        this.email = localStorage.getItem("userID");
    },
    mounted() {
        this.orderHistory();
    },
    computed: {
        filteredOrders() {
            if (!this.startDate || !this.endDate) {
                return this.orderList; // 날짜가 선택되지 않은 경우 원래 주문 목록을 반환합니다
            }

            const startTimestamp = new Date(this.startDate).getTime();
            const endTimestamp = new Date(this.endDate).getTime();

            return this.orderList.filter((order) => {
                const orderDate = new Date(order.ORDER_PAYDATE).getTime();
                return orderDate >= startTimestamp && orderDate <= endTimestamp;
            });
        },
    },
    methods: {
        searchOrders() {
            axios({
                url: "http://localhost:3000/mypage/ordersearchbook",
                method: "get",
                params: {
                    userEmail: this.email,
                    bookKeyword: this.orderKeyword,
                },
            })
                .then((response) => {
                    console.log(response.data);
                    this.orderList = response.data;
                })
                .catch((error) => {
                    console.error("주문 검색 오류:", error);
                });
        },
        setSelectedStatus(status) {
            this.selectedStatus = status;
        },
        fetchOrdersByStatus() {
            if (this.selectedStatus === "전체") {
                // "전체" 옵션을 선택하였을 때, 이미 가져온 전체 주문 목록을 그대로 사용
                this.orderHistory();
            } else {
                // 다른 상태를 선택한 경우, 해당 상태의 주문을 가져와서 표시
                axios({
                    url: "http://localhost:3000/mypage/orderdelivery",
                    method: "get",
                    params: {
                        userEmail: this.email,
                        orderState: this.selectedStatus,
                    },
                })
                    .then((response) => {
                        console.log(response.data);
                        this.orderList = response.data;
                    })
                    .catch((error) => {
                        console.error("배송 상태별 조회 오류:", error);
                    });
            }
        },
        // 주문 목록을 표시하는 코드 및 API 호출 메서드 추가
        orderHistory() {
            axios({
                url: "http://localhost:3000/mypage/orderHistory",
                method: "get",
                params: {
                    userEmail: this.email,
                },
            })
                .then((response) => {
                    console.log(response.data);
                    this.orderList = response.data;

                    // 각 주문 아이템의 BUYCHECK 값을 가져와서 저장합니다.
                    // Promise.all을 사용하여 병렬로 API 요청을 수행합니다.
                    const promises = this.orderList.map((order) => {
                        return Promise.all(
                            order.items.map((item) => {
                                return axios({
                                    url: "http://localhost:3000/mypage/getbuycheck", // 정확한 URL로 수정
                                    method: "get",
                                    params: {
                                        orderId: order.ORDER_ID, // 주문 ID를 전달
                                        userEmail: this.email,
                                    },
                                });
                            })
                        );
                    });

                    // 모든 API 요청이 완료되면 각 아이템의 BUY_CHECK 값을 설정합니다.
                    Promise.all(promises)
                        .then((responses) => {
                            responses.forEach((itemResponses, orderIndex) => {
                                itemResponses.forEach((response, itemIndex) => {
                                    const buyCheckStatus = response.data.buyCheckStatus;
                                    this.orderList[orderIndex].items[itemIndex].BUY_CHECK = buyCheckStatus === 1;
                                });
                            });
                        })
                        .catch((error) => {
                            console.error("BUYCHECK 가져오기 오류:", error);
                        });
                })
                .catch((error) => {
                    console.error("주문내역 가져오기 오류:", error);
                });
        },

        showConfirmationAlert(orderId) {
            const confirmed = window.confirm("구매확정 시 교환 및 환불이 불가능합니다. 진행하시겠습니까?");
            if (confirmed) {
                this.confirmPurchase(orderId);
                window.location.reload();
            } else {
                console.log("구매확정이 취소되었습니다.");
            }
        },

        showCancellationAlert(orderId) {
            const confirmed = window.confirm("주문을 취소하시겠습니까?");
            if (confirmed) {
                this.cancelOrder(orderId);
            } else {
                console.log("주문 취소가 취소되었습니다.");
            }
        },

        cancelOrder(orderId) {
            axios
                .post("http://localhost:3000/mypage/ordercancel", {
                    orderId: orderId,
                    userEmail: this.email,
                })
                .then((response) => {
                    console.log(response.data); //
                    // 주문 취소 성공 시 다시 주문 목록을 가져옵니다.
                    this.fetchOrdersByStatus();
                })
                .catch((error) => {
                    console.error("주문 취소 오류:", error);
                });
        },

        isBuyConfirmed(item) {
            return item.BUY_CHECK; // BUYCHECK 값이 1이면 true, 0이면 false를 반환합니다.
        },

        confirmPurchase(orderId) {
            axios
                .put(`http://localhost:3000/mypage/updatebuycheck/${this.email}/${orderId}`)
                .then((response) => {
                    console.log(response.data);
                    const orderIndex = this.orderList.findIndex((order) => order.ORDER_ID === orderId);
                    if (orderIndex !== -1) {
                        this.orderList[orderIndex].BUY_CHECK = true;

                        // buyConfirmed 값을 변경합니다.
                        this.buyConfirmed = true;
                    }
                })
                .catch((error) => {
                    console.error("구매확정 처리 오류:", error);
                });
        },

        formatCurrency(amount) {
            // 숫자를 통화 형식으로 변환하여 반환
            const currencyString = amount.toLocaleString("ko-KR");

            // 화폐 기호(₩)와 공백을 제거한 후 반환
            return currencyString.replace("₩", "").replace(/\s/g, "");
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
    },
};
</script>
