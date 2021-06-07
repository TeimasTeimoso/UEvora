<template>
    <div class="book">
        <img :src="require('@/assets/' + cover)" alt="book cover" align="left"/>
        <br>
        <div class="book_description">
            <p><b>Name:</b>{{ name }}</p>
            <br>
            <p><b>Author:</b> {{ author }}</p>
            <br>
            <p><b>Year:</b> {{ year }}</p>
            <br>
            <p><b>Description:</b> {{ description }}</p>
        </div>
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

img {
    height: 21rem;
    width: 15rem;
    margin-right: 1rem;
}
</style>