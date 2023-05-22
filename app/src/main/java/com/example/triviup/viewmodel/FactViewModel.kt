import android.app.Application
import androidx.lifecycle.*
import com.example.triviup.network.DataFetchStatus
import com.example.triviup.network.FactResponse
import com.example.triviup.network.NinjaApi
import kotlinx.coroutines.launch
import timber.log.Timber

class FactViewModel (application: Application): AndroidViewModel(application)  {

    private val _fact = MutableLiveData<String>()
    val fact: LiveData<String>
        get() {
            return _fact
        }

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    init {
        getFact()
    }


    fun getFact() {
        viewModelScope.launch {
            try {
                val factResponse: List<FactResponse> = NinjaApi.factRetrofitService.getFacts()
                _fact.value = factResponse[0].fact
                _dataFetchStatus.value = DataFetchStatus.DONE
                println(fact)
            } catch (e: Exception) {
                Timber.tag("error").d("pas de fact: %s", e.message)
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _fact.value = ""
            }
        }
    }
}