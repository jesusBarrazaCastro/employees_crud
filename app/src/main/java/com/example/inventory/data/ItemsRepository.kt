import com.example.inventory.data.Item
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

/**
 * Repository that provides insert, update, delete, and retrieve of [Item] from a given data source.
 */
interface ItemsRepository {

    /**
     * Retrieve all the items from the the given data source.
     */
    suspend fun getAllItemsStream(): Flow<List<Item>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    suspend fun getItemStream(id: Int): Flow<Item?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(item: Item): Item

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(id: Int): Response<Unit>

    /**
     * Update item in the data source
     */
    suspend fun updateItem(id: Int, item: Item): Response<Unit>
}