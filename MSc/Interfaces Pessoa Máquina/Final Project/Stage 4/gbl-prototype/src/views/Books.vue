<template>
    <h1>Popular books</h1>
    <BookItem cover="ricardo_reis.jpeg" name='O Ano da Morte de Ricardo Reis' author='José Saramago' year=1984 description=' Em Camões, o movimento é da terra para o mar; no livro de Saramago temos Ricardo Reis a regressar a Portugal por mar. É substituído o movimento épico da partida. Mais uma vez, a história na escrita de Saramago. E as relações entre a vida e a morte. Ricardo Reis chega a Lisboa em finais de Dezembro e Fernando Pessoa morreu a 30 de Novembro. Ricardo Reis visita-o ao cemitério. Um tempo complexo. O fascismo consolida-se em Portugal.'/>
    <br>
    <br>
    <BookItem cover='clockwork_orange.jpg' name='A Clockwork Orange' author='Anthony Burgess' year=1962 description='In Anthony Burgess influential nightmare vision of the future, criminals take over after dark. Teen gang leader Alex narrates in fantastically inventive slang that echoes the violent intensity of youth rebelling against society. Dazzling and transgressive, A Clockwork Orange is a frightening fable about good and evil and the meaning of human freedom. This edition includes the controversial last chapter not published in the first edition, and Burgess’s introduction, A Clockwork Orange Resucked.'/>
</template>

<script>
import BookItem from '@/components/BookItem.vue'

export default {
    name: 'Books',
    components: {
        BookItem
    }
}
</script>

<style>
body {
    background-color: #dfe3eb;
}

h1 {
    text-align: left;
    text-decoration: underline;
}
</style>