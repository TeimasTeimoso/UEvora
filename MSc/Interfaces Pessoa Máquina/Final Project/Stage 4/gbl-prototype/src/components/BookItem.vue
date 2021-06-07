<template>
    <div class="book">
        <img :src="require('@/assets/' + cover)" alt="book cover" align="left"/>
        <br>
        <p><b>Name:</b>{{ name }}</p>
        <br>
        <p><b>Author:</b> {{ author }}</p>
        <br>
        <p><b>Year:</b> {{ year }}</p>
        <br>
        <p><b>Description:</b> {{ description }}</p>
        <br>
    </div>
</template>

<script>
export default {
    name: 'BookItem',
    props: {
        cover: String,
        name: String,
        author: String,
        year: Number,
        description: String
    }
}
</script>

<style scoped>
p {
    text-align: left;
}
</style>