<template>
    <span v-for="starIndex in 5" :key="'star-' + starIndex">
        <i class="fa-solid fa-star" v-if="starIndex <= filledStars"></i>
        <i class="fas fa-star-half-alt" v-else-if="starIndex - 0.5 === filledStars"></i>
        <i class="fa-regular fa-star" v-else></i>
    </span>
</template>

<script>
export default {
    props: {
        rating: {
            type: Number,
            required: true,
        },
    },
    computed: {
        filledStars() {
            const roundedRating = Math.round(this.rating) / 2; // 반올림하여 0.5 단위로 별점 계산
            return Math.floor(roundedRating); // 채워진 별의 개수 계산
        },
    },
};
</script>

<style scoped>
i:active {
    color: #4e4eff;
}
</style>
