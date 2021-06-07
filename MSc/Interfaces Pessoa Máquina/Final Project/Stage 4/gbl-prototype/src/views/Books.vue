<template>
    <BookItem cover="Memorial_do_convento.jpg" name='Memorial do Convento' author='José Saramago' year=1982 description='Era uma vez um rei que fez promessa de levantar convento em Mafra. Era uma vez a gente que construiu esse convento. Era uma vez um soldado maneta e uma mulher que tinha poderes. Era uma vez um padre que queria voar e morreu doido.'/>
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
