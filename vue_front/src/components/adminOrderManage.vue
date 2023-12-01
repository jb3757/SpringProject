<style scoped src="../../sb-admin-2/vendor/datatables/dataTables.bootstrap4.min.css"></style>
<style scoped src="../assets/css/sbadmin.css"></style>


<template>
    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->
            <SideBar />
            <!-- End Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <nav
                        class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow d-flex align-items-center justify-content-center">
                        <h1><a class="text-gray-800" href="/" style="text-decoration: none;">MOONMOON</a></h1>
                    </nav>
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-flex align-items-center justify-content-between mb-3">
                            <h1 class="h3 mb-2 text-gray-800 col-4">주문 관리</h1>

                        </div>

                        <!-- DataTales Example -->
                        <div class="card shadow mb-4">
                            <div class="card-header py-3 d-flex align-items-center justify-content-between">
                                <h6 class="m-0 font-weight-bold text-primary">주문 목록</h6>

                                <div class="btn-group">
                                    <select class="btn btn-secondary dropdown-toggle" @change="getOrderData"
                                        v-model="filterValue">
                                        <option class="dropdown-item btn cursor-pointer" value="배송준비">배송준비</option>
                                        <option class="dropdown-item btn cursor-pointer" value="배송중">배송중</option>
                                        <option class="dropdown-item btn cursor-pointer" value="배송완료">배송완료</option>
                                        <option class="dropdown-item btn cursor-pointer" value="주문취소">주문취소</option>
                                    </select>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr style="font-weight: bold;">
                                                <th>주문 번호</th>
                                                <th>고객 이메일</th>
                                                <th>주문 날짜</th>
                                                <th>주문 상태</th>
                                                <th>주문 정보</th>
                                                <th class="border-0"></th>
                                                <!-- 전체선택 체크박스 -->
                                                <th class=" border-0 d-flex align-items-center justify-content-center">
                                                    <button @click="allChangeOrderState" type="button"
                                                        class="btn btn-danger">
                                                        전체 변경
                                                    </button>
                                                </th>
                                            </tr>
                                        </thead>
                                        <tfoot>
                                            <tr style="font-weight: bold;">
                                                <th>주문 번호</th>
                                                <th>고객 이메일</th>
                                                <th>주문 날짜</th>
                                                <th>주문 상태</th>
                                                <th>주문 정보</th>
                                                <th class="border-0"></th>
                                                <th class="col-1 border-0 align-middle text-center">
                                                    <button @click="changeOrderState()" type="button"
                                                        class="btn btn-danger border-0 align-middle text-center">
                                                        선택 변경
                                                    </button>
                                                </th>
                                            </tr>
                                        </tfoot>
                                        <tbody>
                                            <tr ref="userData" v-for="(item, i) in displayedPosts" :key="i">

                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    {{
                                                        item.ORDER_ID }}</td>
                                                <td class="col-3 align-middle" style="font-size: small;">
                                                    {{ item.ORDER_USER_EMAIL }}</td>
                                                <td class="col-3 align-middle" style="font-size: small;">
                                                    {{ item.ORDER_DATE }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    {{ item.ORDER_STATE }}</td>
                                                <td class="col-1 align-middle" style="font-size: small;">
                                                    <button
                                                        @click="getOrderDetailData(item.ORDER_ID), getBookDetailData(item.ORDER_ID)"
                                                        type="button"
                                                        class="btn btn-info align-middle ml-2 d-flex align-items-center justify-content-between"
                                                        data-bs-toggle="modal" data-bs-target="#exampleModal">
                                                        주문정보
                                                    </button>
                                                </td>
                                                <!-- 체크박스 -->
                                                <td class="border-0"
                                                    style="font-size: small; width: 1%;">
                                                    <div
                                                        class="form-check">
                                                        <input @change="checkedState(item.ORDER_ID, item.isChcked)"
                                                            class="form-check-input" type="checkbox" v-model="item.isChcked"
                                                            id="flexCheckDefault" style="width: 20px; height: 20px;">
                                                        <label class="form-check-label" for="flexCheckDefault"></label>
                                                    </div>
                                                </td>
                                                <td class="col-1 border-0 align-middle text-center">
                                                    <button @click="changeOrderState(item.ORDER_ID)" type="button"
                                                        class="btn btn-primary border-0 align-middle text-center">
                                                        주문 변경
                                                    </button>
                                                </td>
                                                <!-- 모달 -->
                                                <div class="modal fade position-fixed" id="exampleModal" tabindex="-1"
                                                    aria-labelledby="exampleModalLabel" :aria-hidden="!isModalOpen">
                                                    <div class="modal-dialog modal-lg" style="top: 15%; left: 0">
                                                        <div class="modal-content">
                                                            <div
                                                                class="modal-header d-flex align-items-center justify-content-center">
                                                                <h1 class="modal-title fs-5 text-center"
                                                                    id="exampleModalLabel">주문상세</h1>
                                                            </div>
                                                            <div class="modal-body">
                                                                <h4>주문번호 : <span>{{ orderDetailData.id }}</span></h4>
                                                                <h4>주문자명 : <span>{{ orderDetailData.name }}</span></h4>
                                                                <h4>주문 날짜 : <span>{{ orderDetailData.date }}</span></h4>
                                                                <h4>주문자 이메일 : <span>{{ orderDetailData.email }}</span></h4>
                                                                <h4>주문자 연락처 : <span>{{ orderDetailData.tel }}</span></h4>
                                                                <h4>배송지 주소 : <span>{{ orderDetailData.add }}
                                                                        {{ orderDetailData.addDetail }}</span></h4>
                                                                <h4>우편번호 : <span>{{ orderDetailData.zipCode }}</span></h4>
                                                                <h4>결제금액 : <span>{{ formatNumber(orderDetailData.orderPay) }}원</span></h4>
                                                                <h4>배송상태 : <span>{{ orderDetailData.state }}</span></h4>
                                                                <hr>
                                                                <h4>책 번호 : <span>{{ orderDetailData.bookId }}</span></h4>
                                                                <h4>가격 : <span>{{ formatNumber(orderDetailData.bookPrice) }}원</span></h4>

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary"
                                                                    data-bs-dismiss="modal">닫기</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </tr>


                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- /.container-fluid -->
                    <nav class="d-flex align-items-center justify-content-center" aria-label="Page navigation example">
                        <ul class="pagination">
                            <li class="page-item">
                                <button class="page-link" @click="changePage('first')"
                                    :disabled="currentPage === 1">맨앞</button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" @click="changePage(currentPage - 1)"
                                    :disabled="currentPage === 1">이전</button>
                            </li>
                            <li class="page-item d-flex">
                                <button class="page-link" v-for="pageNumber in pageNumbers" :key="pageNumber"
                                    @click="changePage(pageNumber)"
                                    :class="{ 'active': pageNumber === currentPage, 'disabled': pageNumber === currentPage || currentPage === 1 || currentPage === totalPages }">
                                    {{ pageNumber }}
                                </button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" @click="changePage(currentPage + 1)"
                                    :disabled="currentPage === totalPages">다음</button>
                            </li>
                            <li class="page-item">
                                <button class="page-link" @click="changePage('last')"
                                    :disabled="currentPage === totalPages">맨뒤</button>
                            </li>
                        </ul>

                    </nav>
                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>Copyright &copy; Your Website 2020</span>
                        </div>
                    </div>
                </footer>
                <!-- End of Footer -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Logout Modal-->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                        <a class="btn btn-primary" href="login.html">Logout</a>
                    </div>
                </div>
            </div>
        </div>

    </body>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL = "http://localhost:3000";
axios.defaults.headers.post["Content-Type"] = "application/json;charset=utf-8";
axios.defaults.headers.post["Access-Control-Allow-Origin"] = "*";

import SideBar from '../components/adminSidebar.vue';

export default {
    name: '',
    components: { SideBar },
    data() {
        return {
            orderData: [],
            orderDetailData: {
                id: "",
                email: "",
                date: "",
                name: "",
                tel: "",
                zipCode: "",
                add: "",
                addDetail: "",
                orderPay: "",
                state: "",
            },
            displayedPosts: [
                // 로우 데이터 초기화
            ],

            //체크박스
            orderChecked: [],
            selectAll: false, // 전체 선택 체크박스의 선택 상태

            //답변내용
            answerDetail: "",

            //정렬
            filterValue: "배송준비",

            // 페이징
            perPage: 10, // 한 페이지에 보여줄 게시물 수
            currentPage: 1, // 현재 페이지 번호
            maxDisplayedPages: 9, // 표시할 최대 페이지 수 (현재 페이지를 중심으로 좌우로 표시)
        };
    },

    setup() { },
    created() { },
    mounted() {
        this.getOrderData();
    },
    unmounted() { },
    watch: {
        // selectAll 변수 감시
        selectAll(newStatus) {
            // 모든 체크박스 상태 업데이트
            this.selectList = this.selectList.map(() => newStatus);
        },
    },
    computed: {
        totalPages() {
            return Math.ceil(this.orderData.length / this.perPage);
        },
        displayedPosts() {
            const start = Math.max(0, (this.currentPage - 1) * this.perPage);
            const end = Math.min(this.orderData.length, this.currentPage * this.perPage);
            return this.orderData.slice(start, end);
        },
        pageNumbers() {
            const start = Math.max(1, this.currentPage - Math.floor(this.maxDisplayedPages / 2));
            const end = Math.min(this.totalPages, start + this.maxDisplayedPages - 1);
            return Array.from({ length: end - start + 1 }, (_, i) => start + i);
        },
    },
    methods: {
        async getOrderData() {
            if (this.filterValue === "배송준비") {
                await axios({
                    url: "http://localhost:3000/admin/adminOrderList",
                    method: "POST",
                    data: {
                    },
                })
                    .then((res) => {
                        this.orderData = res.data.filter(item => item.ORDER_STATE === "배송준비");
                        console.log(this.orderData,"배송준비 데이터")
                    })
                    .catch((err) => {
                        alert(err);
                    });
            } else if (this.filterValue === "배송중") {
                await axios({
                    url: "http://localhost:3000/admin/adminOrderList",
                    method: "POST",
                    data: {
                    },
                })
                    .then((res) => {
                        this.orderData = res.data.filter(item => item.ORDER_STATE === "배송중");
                        console.log(this.orderData,"배송준비 데이터")
                    })
                    .catch((err) => {
                        alert(err);
                    });
            } else if (this.filterValue === "배송완료") {
                await axios({
                    url: "http://localhost:3000/admin/adminOrderList",
                    method: "POST",
                    data: {
                    },
                })
                    .then((res) => {
                        this.orderData = res.data.filter(item => item.ORDER_STATE === "배송완료");
                        console.log(this.orderData,"배송준비 데이터")
                    })
                    .catch((err) => {
                        alert(err);
                    });
            } else if (this.filterValue === "주문취소") {
                await axios({
                    url: "http://localhost:3000/admin/adminOrderList",
                    method: "POST",
                    data: {
                    },
                })
                    .then((res) => {
                        this.orderData = res.data.filter(item => item.ORDER_STATE === "주문취소");
                        console.log(this.orderData,"배송준비 데이터")
                    })
                    .catch((err) => {
                        alert(err);
                    });
            }

        },
        async getOrderDetailData(orderId) {
            await axios({
                url: "http://localhost:3000/admin/adminOrderDetail",
                method: "POST",
                data: {
                    order_id: orderId
                },
            })
                .then((res) => {
                    this.orderDetailData.id = res.data[0].ORDER_ID;
                    this.orderDetailData.email = res.data[0].ORDER_USER_EMAIL;
                    this.orderDetailData.date = res.data[0].ORDER_DATE;
                    this.orderDetailData.name = res.data[0].ORDER_NAME;
                    this.orderDetailData.tel = res.data[0].ORDER_TEL;
                    this.orderDetailData.zipCode = res.data[0].ORDER_ZIPCODE;
                    this.orderDetailData.add = res.data[0].ORDER_ADD;
                    this.orderDetailData.addDetail = res.data[0].ORDER_ADDDETAIL;
                    this.orderDetailData.orderPay = res.data[0].ORDER_PAY;
                    this.orderDetailData.state = res.data[0].ORDER_STATE;
                    console.log(this.orderDetailData)
                })
                .catch((err) => {
                    alert(err);
                });
        },
        async getBookDetailData(orderId) { // 주문정보(책번호, 가격)
            await axios({
                url: "http://localhost:3000/admin/adminOrderItemDetail",
                method: "POST",
                data: {
                    order_id: orderId
                },
            })
                .then((res) => {
                    
                    this.orderDetailData.bookId = res.data[0].ORDERITEM_BOOK_ID;
                    this.orderDetailData.bookPrice = res.data[0].ORDERITEM_PRICE;
                    console.log(this.orderDetailData, "이거이거")
                })
                .catch((err) => {
                    alert(err);
                });
        },
        allChangeOrderState() { // 주문상태 전체변경
            this.orderData.map(item => this.orderChecked.push(item.ORDER_ID));
            console.log(this.orderChecked, "전체선택 보려고 만든 콘솔")
            this.changeOrderState();
            alert("전체 주문이 배송중으로 변경되었습니다.");
        },
        checkedState(orderId, isChcked) { //선택한 주문정보 선택
            this.orderChecked.push(orderId);
            console.log(orderId, "오더ID");
            console.log(isChcked, "체크여부");
            console.log(this.orderChecked, "서버로 보낼 오더ID")
        },
        async changeOrderState(orderId) { //주문상태 변경
            this.orderChecked.push(orderId);
            await axios({
                url: "http://localhost:3000/admin/adminStatusChange",
                method: "POST",
                data: {
                    order_id: this.orderChecked
                },
            })
                .then((res) => {
                    alert("배송중으로 변경되었습니다.");
                    this.orderChecked = []; //체크박스 배열 초기화
                    this.getOrderData();
                })
                .catch((err) => {
                    alert(err);
                });
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
        changePage(pageNumber) {
            if (pageNumber === 'first') {
                pageNumber = 1;
            } else if (pageNumber === 'last') {
                pageNumber = this.totalPages;
            } else {
                pageNumber = parseInt(pageNumber);
            }

            window.scrollTo({ top: 0, behavior: 'auto' });
            if (pageNumber >= 1 && pageNumber <= this.totalPages) {
                this.currentPage = pageNumber;
            }
        },
    }
}
</script>
