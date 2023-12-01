<style src="../assets/css/pay.css"></style>
<template>
    <div>
        <gnbBar />
        <div class="payment_total_ex">
            <div class="payment_title">주문/결제</div>
            <div class="progress">
                <span class="before">장바구니> <span class="now">주문/결제</span>> 완료 </span>
            </div>
            <div class="user_info">
                <div class="payment_date">
                    <div class="info1">배송지 정보</div>
                    <div class="info2">
                        <span>내일({{ month }}/{{ nextDay }})</span> 도착 예정
                    </div>
                </div>
                <div class="user_add">
                    <div class="user_info1">
                        <div class="user_name">{{ user_name }}</div>
                        <span> / </span>
                        <div class="user_phone">{{ user_phone }}</div>
                    </div>
                    <div class="user_add_detail">
                        {{ user_zipcode }} {{ user_add1 }} {{ user_add2 }}
                        <!-- 임시 삭제 <button type="button" @click="openAdd">변경</button> -->
                    </div>
                </div>
            </div>
            <div class="payment_book_info">
                <table>
                    <colgroup>
                        <col />
                    </colgroup>
                    <thead>
                        <th>
                            <div class="text">주문 상품</div>
                            <div class="num">총 {{ count }} 개</div>
                        </th>
                    </thead>

                    <tbody>
                        <tr v-for="book in books" :key="book.id">
                            <td class="prod">
                                <div class="prod_area horizontal">
                                    <div class="prod_thumb_box size_sm">
                                        <span class="img_box">
                                            <img :src="book.image" :alt="book.name" />
                                        </span>
                                    </div>
                                    <div class="prod_info_box">
                                        <div class="prod_info">
                                            <div class="prod_name">{{ book.name }}</div>
                                            <div class="book_price">{{ comma(book.price) }}원</div>
                                            <div class="book_q">{{ book.quantity }}개</div>
                                            <div class="book_total">{{ comma(book.totalPrice) }}원</div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="use_coupon">
                <div class="coupon_text">할인 쿠폰</div>
                <select v-model="selectedCoupon" class="choice_coupon">
                    <option disabled value="">쿠폰을 선택하세요</option>
                    <option v-for="coupon in coupons" :key="coupon.name" :value="coupon.ratio">
                        {{ coupon.name }}
                    </option>
                </select>
                <button @click="applyCoupon">사용</button>
                <button v-show="useCoupon" @click="cancelCoupon">취소</button>
            </div>
            <div class="use_point">
                <div class="point_text">포인트</div>
                <input type="number" v-model="usePoint" />
                <button @click="applyPoint">사용</button>
                <div class="user_havePoint">보유 포인트 : &nbsp;</div>
                <div class="user_point">{{ this.point }} P</div>
            </div>
            <div class="pay_area_ex">
                <div class="pay_wrap_ex">
                    <div class="pay_before">
                        <span class="pay_text">상품 금액</span>
                        <span class="won_ex">{{ comma(originalPrice) }} 원</span>
                    </div>
                    <div class="pay_fee">
                        <span class="pay_text">배송비</span>
                        <span class="won_ex" v-bind="calDeliveryFee">{{ comma(this.deliveryFee) }} 원</span>
                    </div>
                    <div class="pay_usePoint">
                        <span class="pay_text">할인 금액</span>
                        <span class="point" v-bind="calDiscountAmount">- {{ comma(this.discountAmount) }} 원</span>
                    </div>
                    <hr />
                    <div class="pay_after">
                        <span class="pay_text">결제 금액</span>
                        <span class="won_ex" v-bind="calFinalPrice">{{ comma(this.finalPrice) }} 원</span>
                    </div>
                    <div class="pay_getPoint">
                        <span class="pay_text">총 적립 포인트</span>
                        <span class="won_ex" v-bind="calEarnPoint">{{ this.earnPoint }} P</span>
                    </div>
                    <div class="pay_button_wrap">
                        <button class="pay_button_ex" type="button" @click="startPay">결제하러 가기</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import gnbBar from "@/components/gnbBar.vue";
import axios from "axios";

export default {
    components: { gnbBar },
    data() {
        return {
            user_name: "",
            user_phone: "",
            user_zipcode: "",
            user_add1: "",
            user_add2: "",
            user_email: ``,
            books: [],
            count: "",
            coupons: [],
            point: 0,
            oldPoint: 0,
            nextDay: "",
            month: "",
            selectedCoupon: "",
            usePoint: 0,
            isModalOpen: true,
            useCoupon: false,
            payID: this.$route.query.payid,
            userEmail: localStorage.getItem("userID"),
            originalPrice: 0,
            deliveryFee: 0,
            discountAmount: 0,
            dfCalPrice: 0,
            finalPrice: 0,
            earnPoint: 0,
            applyCouponPrice: 0,
            applyPointPrice: 0,
            realTimePoint: 0,
        };
    },
    mounted() {
        this.getNextDate();
        this.getUserInfo();
        this.getBookCount();
        this.getBookInfo();
        // this.getCouponList();
        this.getUserPoint();
        this.getOriginalPrice();
        // this.checkState();

        console.log(this.$route.query.payid);
        console.log(this.payID);
    },
    computed: {
        calDiscountAmount() {
            this.discountAmount = this.applyCouponPrice + this.applyPointPrice;
            console.log("discountAmount:", this.discountAmount);
        },
        calDeliveryFee() {
            this.dfCalPrice = this.originalPrice - this.discountAmount;
            console.log("dfCalPrice:", this.dfCalPrice);

            if (this.dfCalPrice < 50000) {
                this.deliveryFee = 2500;
            } else {
                this.deliveryFee = 0;
            }
        },
        calEarnPoint() {
            if (this.usePoint > this.dfCalPrice) {
                this.earnPoint = 0;
            } else {
                this.earnPoint = Math.floor(this.dfCalPrice * 0.05);
            }
        },
        calFinalPrice() {
            this.finalPrice = this.originalPrice - this.discountAmount + this.deliveryFee;
        },
    },
    methods: {
        getUserInfo() { //해결
            const email = this.userEmail;

            axios({
                url: "/pay/payUserInfo",
                method: "post",
                data: { userEmail: email },
            }).then((res) => {
                this.user_name = res.data[0].USER_NAME;
                this.user_phone = res.data[0].USER_PHONE;
                this.user_zipcode = res.data[0].USER_ZIPCODE;
                this.user_add1 = res.data[0].USER_ADD1;
                this.user_add2 = res.data[0].USER_ADD2;
            });
        },
        getNextDate() {
            //내일 배송일로 나오게 하는 함수
            const today = new Date();
            this.nextDay = today.getDate() + 1;
            this.month = today.getMonth() + 1;
        },
        getBookCount() {
            const payid = this.payID;

            axios({
                url: "/pay/bookCount",
                method: "get",
                params: { payID: payid },
            }).then((res) => {
                this.count = res.data;
            });
        },
        getBookInfo() { 
            const payid = this.payID;

            axios({
                url: "/pay/payBookInfo",
                method: "GET",
                params: { payID: payid },
            }).then((res) => {
                this.books = res.data;
                console.log(this.books);
            });
        },
        getCouponList() { //해결
            const email = this.userEmail;

            axios({
                url: "/pay/couponList",
                method: "get",
                params: { userEmail: email },
            }).then((res) => {
                this.coupons = res.data;
                console.log("coupons:", res.data);
            });
        },
        goUsedCoupon() { //해결
            const usedCoupon = {
                selectedCoupon: this.selectedCoupon,
                userEmail: this.userEmail,
            };

            axios({
                url: "/pay/usedCouponStatusChange",
                method: "get",
                params: usedCoupon,
            }).then((res) => {
                console.log("gocouponsuccess");
            });
        },
        applyCoupon() { 
            console.log("selectedCoupon:", this.selectedCoupon);
            console.log("coupons:", this.coupons);
            this.useCoupon = true;
            console.log(this.useCoupon);

            if (this.selectedCoupon === 5) {
                const selectedCouponRatio = 0.05;
                this.applyCouponPrice = this.originalPrice * selectedCouponRatio;
            } else if (this.selectedCoupon === 10) {
                const selectedCouponRatio = 0.1;
                this.applyCouponPrice = this.originalPrice * selectedCouponRatio;
            } else if (this.selectedCoupon === 15) {
                const selectedCouponRatio = 0.15;
                this.applyCouponPrice = this.originalPrice * selectedCouponRatio;
            } else if (this.selectedCoupon === 20) {
                const selectedCouponRatio = 0.2;
                this.applyCouponPrice = this.originalPrice * selectedCouponRatio;
            } else if (this.selectedCoupon === 0) {
                const selectedCouponRatio = 0;
                this.deliveryFee = 0;
                this.applyCouponPrice = this.originalPrice * selectedCouponRatio;
            } else {
                this.applyCouponPrice = this.originalPrice;
            }
            console.log("applyCouponPrice:", this.applyCouponPrice);
        },
        getUserPoint() { //해결
            const email = this.userEmail;

            axios({
                url: "/pay/userPoint",
                method: "get",
                params: { userEmail: email },
            }).then((res) => {
                this.point = res.data;
                this.oldPoint = res.data;
            });
        },
        goUsedPoint() { //보류
            const usedPoint = {
                applyPointPrice: this.applyPointPrice,
                userEmail: this.userEmail,
            };
            axios({
                url: "/pay/delUsedPoint",
                method: "get",
                params: usedPoint,
            }).then((res) => {
                console.log("gocpointsuccess");
            });
        },
        applyPoint() {
            console.log("usePoint = " + this.usePoint);

            if (this.usePoint > this.oldPoint) {
                alert("보유 포인트보다 많은 포인트를 사용할 수 없습니다.");
            } else {
                const previouslyUsedPoint = this.applyPointPrice;
                this.applyPointPrice = this.usePoint;
                this.point = this.point + previouslyUsedPoint - this.applyPointPrice;
            }
            console.log("applyPointPrice:", this.applyPointPrice);
        },
        getOriginalPrice() {  //해결
            const payid = this.payID;

            axios({
                url: "/pay/originalPrice",
                method: "get",
                params: { payID: payid },
            }).then((res) => {
                this.originalPrice = res.data;
            });
        },
        /* goPriceData() {
            const priceData = {
                finalPrice: this.finalPrice,
                deliveryFee: this.deliveryFee,
                applyCouponPrice: this.applyCouponPrice,
                applyPointPrice: this.applyPointPrice,
                earnPoint: this.earnPoint,
                payID: this.payID
            };

            axios({
                url: "/pay/updatePriceData",
                method: "post",
                data: priceData,
            }).then((res) => {
                console.log('success');
            });
        },  */
        openAdd() {
            const confmKey = "devU01TX0FVVEgyMDIzMDgyMjExMTQyODExNDAzODg=";
            const returnUrl = "http://localhost:8080/pay";
            const resultType = "4";
            const useDetailAddr = "N";

            // 주소검색 팝업 API 호출 URL을 생성합니다.
            const apiUrl = `https://business.juso.go.kr/addrlink/addrLinkUrl.do?confmKey=${confmKey}&returnUrl=${returnUrl}&resultType=${resultType}&useDetailAddr=${useDetailAddr}`;

            // 팝업창을 엽니다.
            window.open(apiUrl, "주소검색팝업", "width=570, height=420, toolbar=no, menubar=no, scrollbars=yes, resizable=no");
        },

        startPay() {
            const IMP = window.IMP;
            IMP.init("imp18828153");
            IMP.request_pay(
                {
                    // param
                    pg: "inicis",
                    pay_method: "card",
                    name: this.getBookNames(),
                    amount: `${this.finalPrice}`, //결제 금액
                    buyer_email: `${this.user_email}`,
                    buyer_name: `${this.user_name}`,
                    buyer_tel: `${this.user_phone}`,
                    buyer_addr: `${this.user_add1} ${this.user_add2}`,
                },
                (rsp) => {
                    // callback
                    if (rsp.success) {
                        console.log(rsp.success);
                        let paytype = "";
                        if (rsp.card_name === null) {
                            paytype = "페이";
                        } else {
                            paytype = rsp.card_name;
                        }
                        const priceData = {
                            originalPrice: this.originalPrice,
                            deliveryFee: this.deliveryFee,
                            applyCouponPrice: this.applyCouponPrice,
                            applyPointPrice: this.applyPointPrice,
                            earnPoint: this.earnPoint,
                            payID: this.payID,
                            payMethod: paytype,
                            payState: "배송준비",
                            payCount: this.count,
                        };

                        axios({
                            url: "/pay/updatePriceData",
                            method: "post",
                            data: priceData,
                        })
                            .then((res) => {
                                this.goUsedCoupon();
                            })
                            .then((res) => {
                                this.goUsedPoint();
                            })

                            .then((res) => {
                                if (this.$route.query.usecart) {
                                    axios({
                                        url: "/cart/allDelete",
                                        method: "POST",
                                        data: { email: localStorage.getItem("userID") },
                                    }).then(() => {
                                        this.$router.push({ name: "resultpage", query: { payid: this.payID } });
                                    });
                                } else {
                                    this.$router.push({ name: "resultpage", query: { payid: this.payID } });
                                }
                                alert("결제가 완료되었습니다.");
                            });
                    } else {
                        console.log(rsp);
                        alert("결제에 실패하였습니다.");
                    }
                }
            );
        },
        getBookNames() {
            const payName = `${this.books[0].name} ...외 ${this.count - 1} 개`;
            return payName;
        },
        cancelCoupon() {
            this.useCoupon = false;
            this.selectedCoupon = ""; // 선택된 쿠폰 초기화
            this.applyCouponPrice = 0; // 쿠폰 적용 가격 초기화
        },
        comma(num) {
            return String(num).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
        checkState() {
            console.log(this.payID);

            axios({
                url: "/pay/checkS",
                method: "POST",
                data: { bookid: this.payID },
            })
                .then((res) => {
                    if (res.data === "정상접근") {
                        console.log("ok");
                        return;
                    } else {
                        alert("잘못된 접근입니다.");
                        window.location.href = "/";
                    }
                })
                .catch((err) => {
                    console.log(err);
                });
        },
    },
};
</script>
