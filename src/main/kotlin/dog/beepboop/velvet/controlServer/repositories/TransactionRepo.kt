package dog.beepboop.velvet.controlServer.repositories

import dog.beepboop.velvet.controlServer.models.Transaction
import dog.beepboop.velvet.controlServer.models.TransactionRepoId
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository

@EnableScan
interface TransactionRepo : CrudRepository<Transaction, TransactionRepoId> {
    fun findByUserIdAndTwitchTransactionId(userId: String, transactionId: String): Transaction?
}