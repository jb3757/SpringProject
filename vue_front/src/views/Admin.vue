<style scoped src="bootstrap/dist/css/bootstrap.css"></style>
<style scoped src="../assets/css/sbadmin.css"></style>


<template>
    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->
            <SideBar />
            <!-- End of Sidebar -->
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
                        <div class="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 class="h3 mb-0 text-gray-800">대쉬보드</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row">

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    오늘 매출</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">₩
                                                    {{ formatNumber(weekSales.today) }}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-won-sign fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    전일 대비 매출</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">₩
                                                    {{ formatNumber(weekSales.yesday) }}
                                                    <i v-if="weekSales.yesday > 0" class="fa-solid fa-up-long"></i>
                                                    <i v-else-if="weekSales.yesday < 0" class="fa-solid fa-down-long"></i>
                                                    <i v-else class="fa-solid fa-minus"></i>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-won-sign fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">오늘 방문자 수
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                                                            {{ formatNumber(toDayVisit) }}명</div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="progress progress-sm mr-2">
                                                            <div class="progress-bar bg-info" role="progressbar"
                                                                style="width: 50%" aria-valuenow="50" aria-valuemin="0"
                                                                aria-valuemax="100"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-eye fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Pending Requests Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    회원 수</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    {{ formatNumber(userCount) }}명</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-user fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Content Row -->

                        <div class="row">

                            <!-- Area Chart -->
                            <div class="col-xl-8 col-lg-7">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">주간 매출</h6>

                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="chart-area d-flex align-items-center justify-content-center">
                                            <canvas ref="dailySalesChart" width="900" height="300"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Pie Chart -->
                            <div class="col-xl-4 col-lg-5">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">회원 분포</h6>
                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="chart-area">
                                            <canvas ref="myChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Content Row -->
                        <div class="row">

                            <!-- Content Column -->
                            <div class="col-lg-6 mb-4">

                                <!-- Project Card Example -->
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">공지사항</h6>
                                    </div>
                                    <div class="card-body">
                                        <h4 class="small font-weight-bold ">10월 23일 페이지 점검 <span
                                                class="badge bg-danger">필독</span></h4>
                                        <div class="progress mb-4">
                                            <div class="progress-bar bg-danger" role="progressbar" style="width: 20%"
                                                aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        <h4 class="small font-weight-bold">금일 13:00 ~ 14:00 쿠폰 이벤트 오픈 예정</h4>
                                        <div class="progress mb-4">
                                            <div class="progress-bar bg-warning" role="progressbar" style="width: 40%"
                                                aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>
                                        <h4 class="small font-weight-bold">고객문의 관련 안내<span class="badge bg-danger">필독</span></h4>
                                        <div class="progress mb-4">
                                            <div class="progress-bar" role="progressbar" style="width: 60%"
                                                aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"></div>
                                        </div>

                                    </div>
                                </div>



                            </div>

                            <div class="col-lg-6 mb-4">

                                <!-- Illustrations -->
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">미완료 주문</h6>
                                    </div>
                                    <div class="card-body d-flex flex-column">
                                        <a href="/admin/ordermanage" v-for="(item, i) in orderData"
                                            class="small font-weight-bold mb-2">주문번호 : {{ item.ORDER_ID }} - <span
                                                class="badge bg-danger">미완료</span></a>

                                    </div>
                                </div>

                                <!-- Approach -->
                                <div class="card shadow mb-4">
                                    <div class="card-header py-3">
                                        <h6 class="m-0 font-weight-bold text-primary">미완료 문의</h6>
                                    </div>
                                    <div class="card-body d-flex flex-column">
                                        <a href="/admin/qnamanage" v-for="(item, i) in qnaData" :key="i"
                                            class="small font-weight-bold mb-2">{{ item.QNA_TITLE }} - <span
                                                class="badge bg-danger">미완료</span></a>
                                    </div>
                                </div>

                            </div>
                        </div>

                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>Copyright &copy; Your Website 2023</span>
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
import { ref, onMounted, reactive } from 'vue';
import Chart from 'chart.js/auto';
import 'bootstrap'; // Import Bootstrap JavaScript
import 'chartjs-adapter-luxon';
import SideBar from '../components/adminSidebar.vue';
import axios from 'axios';

export default {
    components: { SideBar },
    data() {
        return {
            //매출
            weekSales: {
                today: "",
                yesday: "",
                twoDayAgo: "",
                threeDayAgo: "",
                fourDayAgo: "",
                fiveDayAgo: "",
            },
            //오늘 포함 일주일전까지의 날짜
            dateRange: [],

            //오늘 방문자수
            toDayVisit: 0,
            userCount: 0,

            //미완료 문의
            qnaData: [],

            //미완료 주문
            orderData: [],
        }
    },
    setup() {
        const myChart = ref(null);
        const dailySalesChart = ref(null);

        // 데이터를 reactive로 정의
        const userSex = reactive({
            female: 0,
            male: 0,
        });


        // 데이터를 업데이트하는 함수 정의
        const updateUserSex = async () => {
            try {
                const response = await axios.post('http://localhost:3000/admin/adminUserLatest', {});
                const data = response.data;

                // 회원수를 저장
                userSex.female = data.filter(item => item.USER_SEX === 'f').length;
                userSex.male = data.filter(item => item.USER_SEX === 'm').length;

                // 차트를 다시 그리는 함수 호출
                drawUserChart();
            } catch (error) {
                alert(error);
            }
        };

        const weekSales = reactive({
            today: "",
            yesday: "",
            twoDayAgo: "",
            threeDayAgo: "",
            fourDayAgo: "",
            fiveDayAgo: "",
            sixDayAgo: "",
        });
        // 데이터를 reactive로 정의
        const dateRange = reactive([]);

        const today = new Date();

        for (let i = 0; i < 7; i++) {
            const date = new Date(today);
            date.setDate(today.getDate() - i);

            // 날짜를 'YYYY-MM-DD' 형식의 문자열로 변환
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0');
            const day = String(date.getDate()).padStart(2, '0');

            const formattedDate = `${year}-${month}-${day}`;

            // dateRange 배열에 reactive 요소로 추가
            dateRange.push(formattedDate);
        }

        // 데이터를 업데이트하는 함수 정의
        const updateSales = async () => {
            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[0] + " 00:00:00",
                    date_end: dateRange[0] + " 23:59:59",
                });
                const res = response.data;

                weekSales.today = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }

            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[1] + " 00:00:00",
                    date_end: dateRange[1] + " 23:59:59",
                });
                const res = response.data;

                weekSales.yesday = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }

            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[2] + " 00:00:00",
                    date_end: dateRange[2] + " 23:59:59",
                });
                const res = response.data;

                weekSales.twoDayAgo = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }

            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[3] + " 00:00:00",
                    date_end: dateRange[3] + " 23:59:59",
                });
                const res = response.data;

                weekSales.threeDayAgo = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }

            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[4] + " 00:00:00",
                    date_end: dateRange[4] + " 23:59:59",
                });
                const res = response.data;

                weekSales.fourDayAgo = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }

            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[5] + " 00:00:00",
                    date_end: dateRange[5] + " 23:59:59",
                });
                const res = response.data;

                weekSales.fiveDayAgo = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }

            try {
                const response = await axios.post('http://localhost:3000/admin/adminDaySales', {
                    date_start: dateRange[6] + " 00:00:00",
                    date_end: dateRange[6] + " 23:59:59",
                });
                const res = response.data;

                weekSales.sixDayAgo = res[0].daySales;
                console.log(res, "이거이거")

                // 차트를 다시 그리는 함수 호출
                drawSalesChart();
            } catch (error) {
                alert(error);
            }
        };

        // 차트를 그리는 함수 정의
        const drawUserChart = () => {
            // 이전 차트 인스턴스 제거
            if (myChart.value && myChart.value.chart) {
                myChart.value.chart.destroy();
            }


            if (myChart.value) {
                const ctx = myChart.value.getContext('2d');
                myChart.value.chart = new Chart(ctx, {
                    type: 'doughnut',
                    data: {
                        labels: ['여성', '남성'],
                        datasets: [
                            {
                                data: [userSex.female, userSex.male],
                                backgroundColor: ['#e74a3b', '#4e73df'],
                            },
                        ],
                    },
                    options: {
                        maintainAspectRatio: false,
                        aspectRatio: 2,
                        cutout: 100,
                        radius: '100%',
                        plugins: {
                            legend: {
                                labels: {
                                    font: {
                                        size: 12,
                                        weight: 'bold',
                                    },
                                    color: '#858796',
                                    pointStyle: 'circle',
                                    usePointStyle: true,
                                },
                            },
                        },
                    },
                });
            }
        };
        //주간 매출 차트
        const drawSalesChart = () => {
            // 이전 차트 인스턴스 제거
            if (dailySalesChart.value && dailySalesChart.value.chart) {
                dailySalesChart.value.chart.destroy();
            }

            if (dailySalesChart) {
                const ctx = dailySalesChart.value.getContext('2d');
                dailySalesChart.value.chart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: [
                            dateRange[6],
                            dateRange[5],
                            dateRange[4],
                            dateRange[3],
                            dateRange[2],
                            dateRange[1],
                            dateRange[0],
                        ],
                        datasets: [
                            {
                                label: '금액', // Add dataset label
                                data: [weekSales.sixDayAgo, weekSales.fiveDayAgo, weekSales.fourDayAgo, weekSales.threeDayAgo, weekSales.twoDayAgo, weekSales.yesday, weekSales.today],
                                backgroundColor: 'rgba(231, 74, 59, 0.1)', // 반투명 배경색 설정
                                borderColor: '#e74a3b', // 선 색상 설정
                                borderWidth: 2, // 선 두께 설정
                                hoverOffset: 1,
                                tension: 0.3, // 곡선의 강도 설정 (0에서 1 사이의 값)
                                fill: true,
                            },
                        ],
                    },
                    options: {
                        responsive: false,
                        plugins: {
                            tooltip: {
                                padding: 10,
                            },
                        },
                    },
                });
            }
        };

        onMounted(() => {
            updateUserSex();
            updateSales();




        });

        return {
            myChart,
            dailySalesChart,
            userSex, // 이렇게 userSex를 반환 객체에 추가
        };
    },
    mounted() {
        this.getSales();
        this.getTodayVisit();
        this.getUserData();
        this.getQnaData();
        this.getOrderData();
    },
    methods: {
        async getSales() {
            const dateRange = [];
            const today = new Date();

            for (let i = 0; i < 7; i++) {
                const date = new Date(today);
                date.setDate(today.getDate() - i);

                // 날짜를 'YYYY-MM-DD' 형식의 문자열로 변환
                const year = date.getFullYear();
                const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1하고 두 자리로 패딩
                const day = String(date.getDate()).padStart(2, '0'); // 일자도 두 자리로 패딩

                const formattedDate = `${year}-${month}-${day}`;

                dateRange.push(formattedDate);
                this.dateRange.push(formattedDate);
            }

            console.log(dateRange[0]);
            await axios({ //오늘매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[0] + " 00:00:00",
                    date_end: dateRange[0] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.weekSales.today = res.data[0].daySales;
                    console.log(res, "이거이거")
                })
                .catch((err) => {
                    alert(err);
                });

            await axios({ //어제매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[1] + " 00:00:00",
                    date_end: dateRange[1] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.weekSales.yesday = res.data[0].daySales;
                    var salesPreviousDay = this.weekSales.today - this.weekSales.yesday;
                    this.weekSales.yesday = parseInt(salesPreviousDay);
                })
                .catch((err) => {
                    alert(err);
                });

            await axios({ //2일전 매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[2] + " 00:00:00",
                    date_end: dateRange[2] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.yesterdaySales = res.data[0].daySales;
                    var salesPreviousDay = this.todaySales - this.yesterdaySales;
                    this.yesterdaySales = parseInt(salesPreviousDay);
                    console.log(this.yesterdaySales);
                })
                .catch((err) => {
                    alert(err);
                });

            await axios({ //3일전 매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[3] + " 00:00:00",
                    date_end: dateRange[3] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.yesterdaySales = res.data[0].daySales;
                    var salesPreviousDay = this.todaySales - this.yesterdaySales;
                    this.yesterdaySales = parseInt(salesPreviousDay);
                    console.log(this.yesterdaySales);
                })
                .catch((err) => {
                    alert(err);
                });

            await axios({ //4일전 매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[4] + " 00:00:00",
                    date_end: dateRange[4] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.yesterdaySales = res.data[0].daySales;
                    var salesPreviousDay = this.todaySales - this.yesterdaySales;
                    this.yesterdaySales = parseInt(salesPreviousDay);
                    console.log(this.yesterdaySales);
                })
                .catch((err) => {
                    alert(err);
                });

            await axios({ //5일전 매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[5] + " 00:00:00",
                    date_end: dateRange[5] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.yesterdaySales = res.data[0].daySales;
                    var salesPreviousDay = this.todaySales - this.yesterdaySales;
                    this.yesterdaySales = parseInt(salesPreviousDay);
                    console.log(this.yesterdaySales);
                })
                .catch((err) => {
                    alert(err);
                });

            await axios({ //6일전 매출
                url: "http://localhost:3000/admin/adminDaySales",
                method: "POST",
                data: {
                    date_start: dateRange[6] + " 00:00:00",
                    date_end: dateRange[6] + " 23:59:59",
                },
            })
                .then((res) => {
                    this.yesterdaySales = res.data[0].daySales;
                    var salesPreviousDay = this.todaySales - this.yesterdaySales;
                    this.yesterdaySales = parseInt(salesPreviousDay);
                    console.log(this.yesterdaySales);
                })
                .catch((err) => {
                    alert(err);
                });
        },
        // 오늘 방문자 수
        async getTodayVisit() {
            // 오늘 날짜
            var today = new Date().toLocaleDateString();
            await axios({
                url: "http://localhost:3000/admin/getTodayV",
                method: "POST",
                data: {
                    today: today
                },
            })
                .then((res) => {
                    this.toDayVisit = res.data[0].vc_count;
                })
                .catch((err) => {
                    alert(err);
                });
        },
        async getUserData() {
            await axios({
                url: "http://localhost:3000/admin/adminUserLatest",
                method: "POST",
                data: {
                },
            })
                .then((res) => {
                    //회원수를 저장
                    this.userCount = res.data.length;

                    //성별에 따른 회원수 저장
                    const femaleMembers = res.data.filter(item => item.USER_SEX === 'f');
                    const maleMembers = res.data.filter(item => item.USER_SEX === 'm');

                    this.userSex.female = femaleMembers.length;
                    this.userSex.male = maleMembers.length;
                    console.log(this.userSex, "회원데이터")
                })
                .catch((err) => {
                    alert(err);
                });
        },

        // 미완료 문의(답변대기 문의들 가져오기)
        async getQnaData() {
            await axios({
                url: "http://localhost:3000/admin/adminQnaWait",
                method: "POST",
                data: {
                },
            })
                .then((res) => {
                    this.qnaData = res.data;
                    console.log(this.qnaData, "미완료 문의 데이터");
                })
                .catch((err) => {
                    alert(err);
                });
        },
        async getOrderData() { //미완료 주문(배송준비 주문들 가져오기)
            await axios({
                url: "http://localhost:3000/admin/adminOrderList",
                method: "POST",
                data: {
                },
            })
                .then((res) => {
                    this.orderData = res.data.filter(item => item.ORDER_STATE === "배송준비");
                    console.log(this.orderData, "배송준비 데이터")
                })
                .catch((err) => {
                    alert(err);
                });
        },
        formatNumber(number) {
            // 숫자를 천 단위마다 쉼표가 있는 형식으로 변환
            return new Intl.NumberFormat().format(number);
        },
    }
};
</script>