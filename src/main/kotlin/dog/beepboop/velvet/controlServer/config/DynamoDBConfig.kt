package dog.beepboop.velvet.controlServer.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.InstanceProfileCredentialsProvider
import com.amazonaws.regions.Regions
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import dog.beepboop.velvet.controlServer.repositories.CommandRepo
import dog.beepboop.velvet.controlServer.repositories.UserRepo
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*

@EnableDynamoDBRepositories(basePackageClasses = [CommandRepo::class,UserRepo::class])
@Configuration
class DynamoDBConfig(@Value("\${aws.key}") private val awsKey: String?, @Value("\${aws.secret}") private val awsSecret: String?) {

    fun awsCredProvider(): AWSCredentialsProvider = AWSStaticCredentialsProvider(awsCredentials())
    @Bean
    @Profile("local")
    fun awsCredentials(): AWSCredentials = BasicAWSCredentials(awsKey, awsSecret)
    @Primary
    @Bean
    fun dynamoDBMapperConfig(): DynamoDBMapperConfig = DynamoDBMapperConfig.DEFAULT
    @Primary
    @Bean
    fun dynamoDBMapper(amazonDynamoDB: AmazonDynamoDB, config: DynamoDBMapperConfig): DynamoDBMapper = DynamoDBMapper(amazonDynamoDB, config)
    @Bean("amazonDynamoDB")
    @Profile("local")
    fun amazonDynamoDBLocal(): AmazonDynamoDB {
        val dynamoDb = AmazonDynamoDBClientBuilder.standard().withCredentials(awsCredProvider()).withRegion(
            Regions.US_EAST_2).build()
        return dynamoDb
    }
    @Bean("amazonDynamoDB")
    @Profile("!local")
    fun amazonDynamoDB(): AmazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withCredentials(InstanceProfileCredentialsProvider(true)).withRegion(Regions.US_EAST_2).build()
}