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
                            <h1 class="h3 mb-0 text-gray-800">회원 분석</h1>
                        </div>


                        <!-- Content Row -->
                        <div class="row d-flex align-items-center justify-content-center">

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-lg-2 col-md-6 mb-4">
                                <div class="card border-bottom-primary shadow h-100">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    남성 회원수</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    {{ formatNumber(userSex.male) }}명</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-user fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-lg-2 col-md-6 mb-4">
                                <div class="card border-bottom-danger shadow h-100">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                                    여성 회원수</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    {{ formatNumber(userSex.female) }}명
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-user fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->


                        </div>

                        <div class="row d-flex align-items-center justify-content-center pb-5">
                            <!-- Pie Chart -->
                            <div class="col-xl-6 col-lg-5">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">성별 분포</h6>
                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="chart-area">
                                            <!-- 성별 도넛차트 -->
                                            <canvas ref="myChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="border border-secondary mb-5"></div>

                        <div class="row d-flex align-items-center justify-content-center">
                            <div v-for="i in 6" :key="i" class="mb-4" style="width: 10%;">
                                <div class="card shadow h-100 mb-5" :class="{
                                    'cardshadow h-100 mb-5 border-bottom-danger': i === 1,
                                    'cardshadow h-100 mb-5 border-bottom-secondary': i === 2,
                                    'cardshadow h-100 mb-5 border-bottom-warning': i === 3,
                                    'cardshadow h-100 mb-5 border-bottom-primary': i === 4,
                                    'cardshadow h-100 mb-5 border-bottom-info': i === 5,
                                    'cardshadow h-100 mb-5 border-bottom-success': i === 6,
                                }">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-secondary text-uppercase mb-1"
                                                    :class="{
                                                        'text-danger': i === 1,
                                                        'text-secondary': i === 2,
                                                        'text-warning': i === 3,
                                                        'text-primary': i === 4,
                                                        'text-info': i === 5,
                                                        'text-success': i === 6,
                                                    }">
                                                    {{ i }}0대
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                                                            {{ formatNumber(ageArangeData[i - 1][i === 1 ? 'teenagers' : i
                                                                === 2 ? 'twenties' : i === 3 ? 'thirties' : i === 4 ? 'forties'
                                                                    : i === 5 ? 'fifties' : 'sixties']) }}명</div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="progress progress-sm mr-2">
                                                            <div class="progress-bar bg-warning" role="progressbar"
                                                                style="width: 50%" aria-valuenow="50" aria-valuemin="0"
                                                                aria-valuemax="100"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fa-solid fa-user fa-xl text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="row d-flex align-items-center justify-content-center pb-5">

                            <!-- Pie Chart -->
                            <div class="col-xl-6 col-lg-5">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">나이대 분포</h6>
                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="chart-area">
                                            <!-- 나이대 파이차트 -->
                                            <canvas ref="myChartAge"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="border border-secondary mb-5"></div>

                        <!-- Content Row -->
                        <div class="row d-flex align-items-center justify-content-center">
                            <div class="col-lg-2 col-md-6 mb-4">
                                <div class="card border-bottom-warning shadow h-100">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    주간 방문자 수
                                                </div>
                                                <div class="row no-gutters align-items-center">
                                                    <div class="col-auto">
                                                        <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                                                            {{ formatNumber(weekVisit) }}</div>
                                                    </div>
                                                    <div class="col">
                                                        <div class="progress progress-sm mr-2">
                                                            <div class="progress-bar bg-warning" role="progressbar"
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
                        </div>

                        <div class="row d-flex align-items-center justify-content-center">
                            <!-- Area Chart -->
                            <div class="col-xl-8 col-lg-7">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 class="m-0 font-weight-bold text-primary">주간 방문자</h6>

                                    </div>
                                    <!-- Card Body -->
                                    <div class="card-body">
                                        <div class="chart-area d-flex align-items-center justify-content-center">
                                            <canvas ref="dailyVisitChart" width="900" height="300"></canvas>
                                        </div>
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
            //유저 나이대
            ageArangeData: [
                {
                    id: 1,
                    teenagers: 0,
                },
                {
                    id: 2,
                    twenties: 0,
                },
                {
                    id: 3,
                    thirties: 0,
                },
                {
                    id: 4,
                    forties: 0,
                },
                {
                    id: 5,
                    fifties: 0,
                },
                {
                    id: 6,
                    sixties: 0,
                },
            ],
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

            //주간 방문자 수
            weekVisit: 0,

            //미완료 문의
            qnaData: [],

            //미완료 주문
            orderData: [],
        }
    },
    setup() {
        const myChart = ref(null);
        const myChartAge = ref(null);
        const dailyVisitChart = ref(null);

        // 성별 데이터를 reactive로 정의
        const userSex = reactive({
            female: 0,
            male: 0,
        });
        // 나이대 데이터를 reactive로 정의
        const ageArangeData = reactive(
            [
                {
                    id: 1,
                    teenagers: 0,
                },
                {
                    id: 2,
                    twenties: 0,
                },
                {
                    id: 3,
                    thirties: 0,
                },
                {
                    id: 4,
                    forties: 0,
                },
                {
                    id: 5,
                    fifties: 0,
                },
                {
                    id: 6,
                    sixties: 0,
                },
            ]
        );

        var weekVisit = reactive([
            {
                date: "2023-01-01",
                count: 0,
            },
            {
                date: "2023-01-01",
                count: 0,
            },
            {
                date: "2023-01-01",
                count: 0,
            },
            {
                date: "2023-01-01",
                count: 0,
            },
            {
                date: "2023-01-01",
                count: 0,
            },
            {
                date: "2023-01-01",
                count: 0,
            },
            {
                date: "2023-01-01",
                count: 0,
            },
        ]);

        //----------------------------------여기부터 서버에서 데이터 불러오는 함수-------------------------------------
        // 성별&나이대 데이터를 업데이트하는 함수 정의
        const updateUserSex = async () => {
            try {
                const response = await axios.post('http://localhost:3000/admin/adminUserLatest', {});
                const data = response.data;

                // 회원수를 저장
                userSex.female = data.filter(item => item.USER_SEX === 'f').length;
                userSex.male = data.filter(item => item.USER_SEX === 'm').length;

                //회원 나이대를 저장
                ageArangeData[0].teenagers = data.filter(item => item.USER_AGEGROUP === "10").length;
                ageArangeData[1].twenties = data.filter(item => item.USER_AGEGROUP === "20").length;
                ageArangeData[2].thirties = data.filter(item => item.USER_AGEGROUP === "30").length;
                ageArangeData[3].forties = data.filter(item => item.USER_AGEGROUP === "40").length;
                ageArangeData[4].fifties = data.filter(item => item.USER_AGEGROUP === "50").length;
                ageArangeData[5].sixties = data.filter(item => item.USER_AGEGROUP === "60").length;
                console.log(ageArangeData, "나이대 차트 데이터")
                // 차트를 다시 그리는 함수 호출
                drawUserChart(); // 성별 차트
                drawUserArangeChart(); // 나이대 차트
            } catch (error) {
                alert(error);
            }
        };

        // 주간 방문자 수 데이터를 업데이트하는 함수 정의
        const updateWeekVisit = async () => {
            try {
                const response = await axios.post('http://localhost:3000/admin/getTotalV', {});
                const data = response.data.slice(0, 7);

                // 데이터를 주간 방문자 수 배열에 할당
                weekVisit = data.map(item => {
                    return {
                        date: item.vc_date,
                        count: item.vc_count
                    };
                });

                console.log(weekVisit, "주간 방문자 수 데이터");

                // 차트를 다시 그리는 함수 호출
                drawWeekVisitChart();
            } catch (error) {
                alert(error);
            }
        };

        //----------------------------------여기부터 차트 함수-------------------------------------
        // 성별 회원 차트를 그리는 함수 정의
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

        // 나이대 차트를 그리는 함수 정의
        const drawUserArangeChart = () => {
            // 이전 차트 인스턴스 제거
            if (myChartAge.value && myChartAge.value.chart) {
                myChartAge.value.chart.destroy();
            }


            if (myChartAge.value) {
                const ctx = myChartAge.value.getContext('2d');
                myChartAge.value.chart = new Chart(ctx, {
                    type: 'pie', // 차트 종류를 'pie'로 설정
                    data: {
                        labels: ['10대', '20대', '30대', '40대', '50대', '60대'],
                        datasets: [
                            {
                                data: [ageArangeData[0].teenagers,
                                ageArangeData[1].twenties,
                                ageArangeData[2].thirties,
                                ageArangeData[3].forties,
                                ageArangeData[4].fifties,
                                ageArangeData[5].sixties
                                ], // 남성과 여성의 데이터를 배열로 설정
                                backgroundColor: ['#E74A3B', '#858796', '#F6C23E', '#4E73DF', '#36B9CC', '#1CC88A'],
                            },
                        ],
                    },
                    options: {
                        maintainAspectRatio: false,
                        aspectRatio: 1, // 파이 차트는 보통 1:1의 비율을 가집니다.
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

        const drawWeekVisitChart = () => {
            // 일별 방문자데이터들의 최대값 가져오기
            const maxCount = Math.max(...weekVisit.map(item => item.count));

            // 이전 차트 인스턴스 제거
            if (dailyVisitChart.value && dailyVisitChart.value.chart) {
                dailyVisitChart.value.chart.destroy();
            }

            if (dailyVisitChart) {
                const ctx = dailyVisitChart.value.getContext('2d');
                dailyVisitChart.value.chart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: [
                            weekVisit[6].date,
                            weekVisit[5].date,
                            weekVisit[4].date,
                            weekVisit[3].date,
                            weekVisit[2].date,
                            weekVisit[1].date,
                            weekVisit[0].date,
                        ],
                        datasets: [
                            {
                                label: '방문자 수', // Add dataset label
                                data: [weekVisit[0].count, weekVisit[1].count, weekVisit[2].count, weekVisit[3].count, weekVisit[4].count, weekVisit[5].count, weekVisit[6].count],
                                backgroundColor: '#F6C23E', // 반투명 배경색 설정
                                borderColor: '#F6C23E', // 선 색상 설정
                                hoverOffset: 1,
                                fill: true,
                            },
                        ],
                    },
                    options: {
                        responsive: false,
                        scales: {
                            x: {
                                grid: {
                                    display: false, // 배경 세로 선 숨기기
                                },
                            },
                            y: {
                                beginAtZero: true, // y 축의 값이 0부터 시작하도록 설정

                            },
                        },
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
            updateWeekVisit();




        });

        return {
            myChart,
            myChartAge,
            dailyVisitChart,
            userSex, // 이렇게 userSex를 반환 객체에 추가
        };
    },
    mounted() {
        this.getUserData();
        this.updateWeekVisit();
    },
    methods: {
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

                    //나이대 저장

                    this.ageArangeData[0].teenagers = res.data.filter(item => item.USER_AGEGROUP === "10").length;
                    this.ageArangeData[1].twenties = res.data.filter(item => item.USER_AGEGROUP === "20").length;
                    this.ageArangeData[2].thirties = res.data.filter(item => item.USER_AGEGROUP === "30").length;
                    this.ageArangeData[3].forties = res.data.filter(item => item.USER_AGEGROUP === "40").length;
                    this.ageArangeData[4].fifties = res.data.filter(item => item.USER_AGEGROUP === "50").length;
                    this.ageArangeData[5].sixties = res.data.filter(item => item.USER_AGEGROUP === "60").length;
                    console.log(this.ageArangeData, "나이대")

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
        // 주간 방문자수 데이터 받기
        async updateWeekVisit() {
            await axios({
                url: "http://localhost:3000/admin/getTotalV",
                method: "POST",
                data: {
                },
            })
                .then((res) => {
                    const data = res.data.slice(0, 7);

                    //이전 1주일간 매일 방문자수를 저장
                    data.map(item => this.weekVisit += item.vc_count);
                    console.log(this.weekVisit, "주간방문자수 데이터")
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