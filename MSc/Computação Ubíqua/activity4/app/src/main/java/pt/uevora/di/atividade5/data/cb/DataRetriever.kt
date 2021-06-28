package pt.uevora.di.atividade5.data.cb

import pt.uevora.di.atividade5.data.model.Breed

interface DataRetriever {

    fun onDataFetchedSuccess(breeds: List<Breed>)

    fun onDataFetchedFailed()
}