<template>
    <!-- toTop -->
    <div v-show="state.showTopButton" @click="scrollToTop" class="scroll_top_btn">
        <div>
            <span>TOP</span>
        </div>
    </div>
</template>

<script>
import { ref, reactive, onMounted, onUnmounted } from "vue";

export default {
    name: '',
    components: {},
    data() {
        return {
            sampleData: ''
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
            const duration = 200;
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
    created() { },
    mounted() { },
    unmounted() { },
    methods: {}
}
</script>