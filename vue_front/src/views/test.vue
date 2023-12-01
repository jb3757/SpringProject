<template>
  <div class="inner">
    <div class="star-rating">
      <div
        class="star"
        v-for="index in 5"
        :key="index"
        @mouseover="check(index)"
        @click="alert_check(index)"
      >
        <span v-if="index <= score">🍎</span>
        <span v-if="index > score">🍏</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Star",
  data() {
    return {
      score: 0,
      mouseover_evnet: true,
    };
  },
  methods: {
    check(index) {
      if (this.mouseover_evnet) {
        this.score = index;
      }
    },
    alert_check(index) {
      this.score = index;
      alert(this.score);

      this.mouseover_evnet = false;
    },
  },
  mounted() {
    console.log(this.score);
  },
};
</script>

<style scoped>
.star {
  display: inline-block;
}
.star span {
  padding-left: 10px;
}
</style>
