<template>
    <span v-for="starIndex in 5" :key="'star-' + starIndex">
        <i class="fa-solid fa-star" v-if="starIndex <= filledStars"></i>
        <i class="fa-solid fa-star-half-stroke" v-else-if="starIndex - 0.5 === filledStars"></i>
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
            return this.rating;
        },
    },
};
</script>

<style scoped>
i {
    color: #4e4eff;
}
</style>
