import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.triviup.model.Rank
import com.example.triviup.network.FactResponse
import com.example.triviup.network.NinjaApi
import kotlinx.coroutines.launch

class FactViewModel (application: Application): AndroidViewModel(application)  {

    private val _fact = MutableLiveData<String>()
    val fact: LiveData<String>
        get() {
            return _fact
        }

    init {
        getFact()
    }


    fun getFact() {
        viewModelScope.launch {
            try {
                val factResponse: List<FactResponse> = NinjaApi.factRetrofitService.getFacts()
                _fact.value = factResponse[0].fact
                println(fact)
            } catch (e: Exception) {
                Log.d("error", "pas de fact: ${e.message}")
                _fact.value = ""
            }
        }
    }
}