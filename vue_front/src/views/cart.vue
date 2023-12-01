<style src="../assets/css/cart.css"></style>

<template>
    <gnbbar ref="childComponent" />
    <div class="cart_container">
        <div class="cart_wrap">
            <div class="cart_header" style="background-color: white">
                <input class="allselect_cart" type="checkbox" v-model="selectAll" @change="selectAllItems" id="book_checkbox" /><label
                    for="book_checkbox"
                ></label>
                <h3>&nbsp;장바구니</h3>
                <button type="button" class="delete_button" @click="deleteCartBook">삭제</button>
            </div>
            <div class="cart_float">
                <div class="cart_inside">
                    <ol class="cart_list">
                        <span v-if="cart.length == 0" class="if_nodata">
                            <div class="nodata"><img src="../assets/img/nodata.png" /></div>
                            <br /><br />
                            <p>장바구니에 담긴 상품이 없어요</p>
                        </span>
                        <span v-else-if="cart.length >= 1">
                            <li v-for="(book, index) in cart" :key="index">
                                <div class="cart_book_info_set">
                                    <div class="cart_book_info_img_set">
                                        <input
                                            class="select_cart"
                                            type="checkbox"
                                            v-model="book.checked"
                                            @change="insertOrRemove(book)"
                                            :id="'book_checkbox_' + index"
                                        />
                                        <label :for="'book_checkbox_' + index"></label>
                                        <img class="cart_img" :src="book.img" />

                                        <div class="cart_book_info_left">
                                            <span class="cart_book_name">{{ book.title }}</span>
                                            <button type="button" class="deleteBook" @click="deleteBook(book)"></button>
                                            <span class="cart_book_price">{{ comma(book.price) }} 원</span>
                                            <span class="cart_book_point">적립포인트 &nbsp;&nbsp;&nbsp;{{ getPoint(book) }}</span>
                                        </div>
                                    </div>

                                    <div class="changeQua">
                                        <div class="button">
                                            <button @click="decreaseQuantity(book)">-</button>
                                            <span>{{ book.quantity }}</span>
                                            <button @click="increaseQuantity(book)">+</button>
                                        </div>
                                        <br />
                                        <p>{{ comma(getPrice(book)) }} 원</p>
                                    </div>
                                </div>
                            </li>
                        </span>
                    </ol>
                    <div class="footer"></div>
                </div>

                <!-- CSS작업시 컨텐츠를 박스라고 생각하면 이해가 쉬움 
             payment_area라는 최상위 부모박스 > 형제박스(pay_wrap 자식박스, pay_wrap 자식박스)
        -->
                <div class="payment_area">
                    <div class="pay_wrap">
                        <div class="payment_value">상품금액</div>
                        <div class="getTotalPrice">{{ comma(getTotalPrice) }}</div>
                        <div class="unit">원</div>
                    </div>

                    <div class="pay_wrap_second">
                        <div class="payment_value">배송비</div>
                        <span class="getTotalPrice">{{ comma(fee) }} </span>
                        <span class="unit">원</span>
                    </div>

                    <br /><br /><br />
                    <hr />
                    <div class="pay_wrap">
                        <div class="payment_text">결제금액</div>
                        <div class="payment_total">{{ comma(getFinalPrice) }}</div>
                        <span class="unit">원</span>
                    </div>
                    <div class="pay_wrap_second">
                        <div class="payment_text">총 적립 포인트</div>
                        <div class="payment_point">{{ totalPoint }}</div>
                        <span class="unit">P</span>
                    </div>
                    <button @click="choosePay" class="pay_button">주문하기</button>
                    <!-- <button @click="allPay">전체 결제</button> -->
                </div>
            </div>
        </div>
    </div>
    <div class="progress">
        <span class="first">장바구니</span>&nbsp;
        <span class="right">> 주문/결제 > 완료</span>
    </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import gnbbar from "../components/gnbBar.vue";

export default {
    components: { gnbbar },
    data() {
        return {
            cart: [],
            select: [],
            selectAll: false,
            userEmail: localStorage.getItem("userID"),
        };
    },
    filters: {
        comma(val) {
            return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
    },
    methods: {
        comma(num) {
            return String(num).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
        deleteCartBook() {
            if (this.select.length === 0) {
                alert("상품을 선택하세요");
            }
            console.log(this.select);
            axios({
                url: "/cart/delete",
                method: "POST",
                data: {
                    bookid: this.select,
                },
            })
                .then((res) => {
                    if (res.data === "삭제완료") {
                        alert("삭제완료");
                        this.getCart();
                        this.$refs.childComponent.getCartNum();
                    }
                })
                .catch((err) => {
                    console.error(err);
                });
        },
        increaseQuantity(book) {
            //장바구니 수량 늘리기

            book.quantity++;
        },
        deleteBook(book) {
            console.log(book.book_no);
            if (confirm("장바구니에서 제거하시겠습니까?")) {
                axios({
                    url: "/cart/deleteCartBook",
                    method: "GET",
                    params: { bookid: book.book_no },
                })
                    .then((res) => {
                        alert("제거되었습니다.");
                        this.$refs.childComponent.getCartNum();
                        this.getCart();
                    })
                    .catch((err) => {
                        console.log(err);
                    });
            } else {
                return;
            }
        },
        choosePay() {
            // 선택된 상품들만 필터링하여 가져옵니다.
            if (!localStorage.getItem("userID")) {
                alert("로그인해주세요");
                return;
            }
            const selectedBooks = this.cart.filter((book) => this.select.includes(book.book_no));

            if (selectedBooks.length == 0) {
                alert("상품을 선택해주세요");
                return;
            }

            let TP = 0; //총 결제 금액
            let TP2 = 0; //총 포인트
            let thisFee = 0;

            let bookData = [];

            selectedBooks.forEach((book) => {
                TP += this.getPrice(book);
                TP2 += this.getPoint(book);

                bookData.push({
                    ORDERITEM_BOOKID: book.book_no,
                    ORDERITEM_CNT: book.quantity,
                    ORDERITEM_PRICE: book.price,
                    ORDERITEM_POINT: this.getPoint(book),
                });
            });

            if (TP >= 50000) {
                //결제금액이 50000원이상이면 배송료 0원으로 설정
                thisFee = 0;
            } else if (TP < 50000) {
                thisFee = 2500;
            }

            axios({
                url: "http://localhost:3000/cart",
                method: "POST",
                data: {
                    email: this.userEmail,
                    total_pay: TP,
                    total_point: TP2,
                    fee: thisFee,
                    books_info: bookData,
                },
            })
                .then((res) => {
                    if (res.status == 401) {
                        console.log("에러 발생: " + res.data.error);
                    } else if (res.status == 200) {
                        const orderId = res.data.orderID;
                        alert("결제페이지로 이동합니다");
                        console.log(orderId);
                        this.$router.push({ name: "paymentPage", query: { payid: orderId, usecart: "use" } });
                    }
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        decreaseQuantity(book) {
            //장바구니 수량 줄이기
            if (book.quantity === 1) {
                //한개 이상은 못줄이게 하기
                return;
            }
            book.quantity--;
        },
        insertOrRemove(book) {
            // 배열에 book.book_no가 없으면 해당 값을 추가
            // 배열에 이미 book.book_no가 있으면 해당 값을 제거하는 filter 적용

            if (book.checked && !this.select.includes(book.book_no)) {
                this.select.push(book.book_no); // 체크되어 있고, 배열에 없는 경우 추가
            } else if (!book.checked && this.select.includes(book.book_no)) {
                this.select = this.select.filter((selectedBookNo) => selectedBookNo !== book.book_no); // 체크가 해제되고, 배열에 있는 경우 제거
            }
            console.log(this.select);
        },
        selectAllItems() {
            for (const book of this.cart) {
                book.checked = this.selectAll;
                this.insertOrRemove(book);
            }
        },
        handleChildCheckboxChange() {
            // 모든 자식 체크박스가 선택되어 있는지 확인합니다.
            console.log(this.cart);
            if (this.cart.length == 0) {
                this.selectAll = false;
                return;
            }
            const allChildChecked = this.cart.every((book) => book.checked);

            // 부모 체크박스의 상태를 업데이트합니다.
            this.selectAll = allChildChecked;
        },
        getCart() {
            if (!localStorage.getItem("userID")) {
                this.cart = [];
                return;
            }
            const email = this.userEmail;

            axios({
                url: "/cart/getInfo",
                method: "POSt",
                data: {
                    email: email,
                },
            })
                .then((res) => {
                    this.cart = res.data;
                    console.log(this.cart);
                })
                .catch((err) => {
                    console.log(err);
                });
        },
    },
    computed: {
        // 데이터 값이 바뀌면 실행 되는것?
        getPrice(book) {
            //버튼으로 수량을 바꾸면 실행
            return (book) => book.price * book.quantity; //값 * 수량
        },
        getPoint(book) {
            //버튼으로 수량바꾸면 실행
            const point = (book) => book.point * book.quantity; //마일리지 * 수량
            return point;
        },
        getTotalPrice() {
            //선택한 상품들 총합 가격
            const selectedBooks = this.cart.filter((book) => this.select.includes(book.book_no)); //책 정보가 있는 배열에서 체크가 된것만 필터링

            if (selectedBooks.length == 0) {
                return 0;
            }

            const totalPrice = selectedBooks.reduce((total, book) => total + book.price * book.quantity, 0); //선택된것만 값 총합 가져오기

            return totalPrice; //리턴
        },
        fee() {
            if (this.getTotalPrice > 50000) {
                return 0;
            } else {
                return 2500;
            }
        },
        getFinalPrice() {
            //최종 결제 가격 함수
            if (this.getTotalPrice + this.fee == 2500) {
                return 0;
            }
            return this.getTotalPrice + this.fee;
        },
        totalPoint() {
            //결제 시 얻는 포인트
            const selectedBooks = this.cart.filter((book) => this.select.includes(book.book_no)); //책 정보가 있는 배열에서 체크가 된것만 필터링

            const totalPoint = selectedBooks.reduce((total, book) => total + book.point * book.quantity, 0); //선택된것만 값 총합 가져오기

            return totalPoint;
        },
    },

    mounted() {
        this.getCart();
    },
    watch: {
        // 자식 체크박스 상태 변경 감지
        cart: {
            handler() {
                this.handleChildCheckboxChange();
            },
            deep: true,
        },
    },
};
</script>
