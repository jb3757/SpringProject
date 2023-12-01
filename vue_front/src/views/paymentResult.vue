<template>
    <gnbbar />
    <div class="pay_title">주문/결제</div>

    <div class="progress">
        <span class="before">장바구니> 주문/결제> </span>
        <span class="now">완료</span>
    </div>

    <div class="paymentResult_wrap">
        <div class="result_title"><span class="text_color">주문이 완료</span>되었습니다.</div>
        <div class="payment_info">
            <span>주문번호</span><span class="payment_no">{{ payment_no }}</span> <br /><br />
            <span>구매내역</span><span class="pay_result_title">{{ bookTitle_data[0]?.BOOK_TITLE }}</span
            ><span v-if="bookTitle_data.length > 1"> 외 {{ bookTitle_data.length - 1 }} 개</span><br />
            <br /><br /><br />
            <div class="payresult_user_info">
                <div class="payresult_user_info2">
                    <div>배송지 정보</div>
                    <div class="user_name">{{ name }}</div>
                </div>
                <br />
                <div class="user_phone">{{ phone }}</div>
                <br />
                <div class="user_add">{{ add }}</div>
            </div>
        </div>

        <div class="payresult_bill">
            <div class="logo"></div>
            <div class="bill_top">
                <div class="cost">
                    <div class="payment_cost_text">주문금액</div>
                    <div class="payment_cost1">{{ payInfo[0]?.ORDER_PAY }} 원</div>
                </div>
                <div class="cost_book">
                    <div class="payment_cost_text">상품금액</div>
                    <div class="payment_cost2">{{ payInfo[0]?.price }} 원</div>
                </div>
                <div class="cost_fee">
                    <div class="payment_cost_text">배송비</div>
                    <div class="payment_cost3">{{ payInfo[0]?.ORDER_COST }} 원</div>
                </div>
                <div class="cost_coupon">
                    <div class="payment_cost_text">쿠폰할인</div>
                    <div class="payment_cost4">-{{ payInfo[0]?.ORDER_COUPON }} 원</div>
                </div>
                <div class="cost_point">
                    <div class="payment_cost_text">사용한 포인트</div>
                    <div class="payment_cost5">{{ payInfo[0]?.ORDER_USEPOINT }} P</div>
                </div>
            </div>

            <div class="bill_bottom">
                <div class="pay_result">결제 상세</div>
                <div class="pay_info">
                    <div class="card_info">{{ payInfo[0]?.ORDER_PAYMETHOD }}</div>
                    <div class="pay_cost">{{ payInfo[0]?.ORDER_PAY }} 원</div>
                    <br />
                </div>
                <div class="pay_date">{{ payInfo[0]?.date }}</div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import gnbbar from "../components/gnbBar.vue";

export default {
    components: { gnbbar },
    data() {
        return {
            name: "",
            phone: "",
            add: "",
            payment_no: this.$route.query.payid,
            bookTitle_data: [],
            payInfo: [],
        };
    },
    computed: {},
    mounted() {
        this.getBookTitle();
        this.getPayInfo();
        this.getUserInfo();
    },
    methods: {
        getBookTitle() {
            const cartNum = this.payment_no;

            axios({
                url: "/payresult",
                method: "GET",
                params: { cartNum },
            }).then((res) => {
                this.bookTitle_data = res.data;
                this.cutTitle();

                console.log(this.bookTitle_data[0].BOOK_TITLE);
            });
        },
        cutTitle() {
            const max_title_len = 24;
            console.log(this.bookTitle_data[0].BOOK_TITLE);

            const first_booktitle = this.bookTitle_data[0].BOOK_TITLE;
            if (first_booktitle.length > max_title_len) {
                this.bookTitle_data[0].BOOK_TITLE = this.bookTitle_data[0].BOOK_TITLE.slice(0, max_title_len) + " ...";
            } else {
                return this.bookTitle_data[0].BOOK_TITLE;
            }
        },
        getPayInfo() {
            axios({
                url: "/payresult/info",
                method: "POST",
                data: { payid: this.payment_no },
            })
                .then((res) => {
                    this.payInfo = res.data;
                    console.log(this.payInfo[0].ORDER_PAY);
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        getUserInfo() {
            const email = localStorage.getItem("userID");
            const payid = this.payment_no;

            axios({
                url: "/payresult/getUser",
                method: "POST",
                data: {
                    email: email,
                    payid: payid,
                },
            }).then((res) => {
                this.name = res.data[0].USER_NAME;
                this.phone = res.data[0].USER_PHONE;
                this.add = res.data[0].USER_ADD1 + " " + res.data[0].USER_ADD2;
            });
        },
    },
};
</script>

<style scoped>
.payresult_user_info2 {
    display: flex;
}
.user_name {
    margin-left: 4.6%;
}
.user_phone {
    margin-left: 12%;
}
.user_add {
    margin-left: 12%;
    text-align: left;
}
.pay_result_title {
    margin-left: 80px;
}
.payment_no {
    margin-left: 80px;
    text-align: right;
    color: #4e4eff;
}
.payment_info {
    margin-top: 12%;
    margin-left: 6%;
    font-weight: bold;
    font-size: 1.2em;
}
.paymentResult_wrap {
    position: absolute;
    border: none;
    width: 70%;
    height: 55%;
    margin-left: 15%;
    margin-right: 15%;
    margin-top: 5%;
    box-shadow: 0 0 15px #ccc;
    border-radius: 10px;
    background-color: white;
}
.payresult_bill {
    position: relative;
    border: 1px solid #000000;
    width: 30%;
    height: 100%;
    top: -420px;
    right: -830px;
    background-image: url("../assets/img/bill.png");
    background-size: 100%;
    background-position: center;
    background-repeat: no-repeat;
    border: none;
}
.result_title {
    position: relative;
    top: 100px;
    margin-left: 6%;
    font-weight: bold;
    font-size: 1.5em;
}
.result_title span {
    color: #4e4eff;
}
.bill_top {
    position: relative;
    top: 130px;
    border-top: 1px solid #999999;
    width: 90%;
    left: 19px;
}
.payment_cost {
    text-align: right;
}
.cost {
    display: flex;
    position: relative;
    font-weight: bold;
    font-size: 1.1em;
    margin-left: 7%;
    margin-top: 5%;
    justify-content: space-between;
}
.cost_book {
    display: flex;
    position: relative;
    margin-top: 7%;
    margin-left: 7%;
    justify-content: space-between;
}
.cost_fee {
    display: flex;
    position: relative;
    margin-top: 4%;
    margin-left: 7%;
    justify-content: space-between;
}
.cost_coupon {
    display: flex;
    position: relative;
    margin-top: 4%;
    margin-left: 7%;
    justify-content: space-between;
}
.cost_point {
    display: flex;
    position: relative;
    margin-top: 4%;
    margin-left: 7%;
    justify-content: space-between;
}
.bill_bottom {
    position: relative;
    top: 180px;
    border-top: 1px solid #999999;
    width: 90%;
    left: 19px;
}
.pay_result {
    margin-top: 15px;
    margin-left: 7%;
    font-weight: bold;
    font-size: 1.1em;
}
.pay_info {
    display: flex;
    margin-left: 7%;
    margin-top: 20px;
}
.pay_cost {
    margin-left: 180px;
    font-weight: bold;
}
.pay_date {
    margin-left: 7%;
    margin-top: 20px;
}
.payment_cost1 {
    position: relative;
    margin-right: 19px;
    text-align: right;
    float: right;
}
.payment_cost2 {
    position: relative;
    margin-right: 19px;
    text-align: right;
    float: right;
}
.payment_cost3 {
    position: relative;
    margin-right: 19px;
    text-align: right;
    float: right;
}
.payment_cost4 {
    position: relative;
    margin-right: 19px;
    text-align: right;
    float: right;
}
.payment_cost5 {
    position: relative;
    margin-right: 19px;
    text-align: right;
    float: right;
}
.progress {
    position: absolute;
    font-size: 1.4em;
    top: 22%;
    right: 15%;
    font-weight: bolder;
}
.progress .before {
    color: rgb(158, 158, 158);
}
.progress .now {
    color: #4e4eff;
}
.pay_title {
    position: absolute;
    top: 22%;
    left: 15%;
    font-size: 1.6em;
    font-weight: bolder;
}
</style>
