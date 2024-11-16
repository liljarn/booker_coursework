package ru.liljarn.booker.infrastructure.grpc

import kotlinx.coroutines.runBlocking
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import ru.liljarn.gandalf.user.UserDataServiceGrpcKt
import ru.liljarn.gandalf.user.getUserDataIdRequest
import ru.liljarn.gandalf.user.getUserDataJwtRequest
import java.util.UUID

@Service
class GandalfService {

    @GrpcClient("gandalf")
    private lateinit var gandlafClient: UserDataServiceGrpcKt.UserDataServiceCoroutineStub

    fun getUserByToken(token: String) = runBlocking {
        gandlafClient.getUserDataByJwt(getUserDataJwtRequest { jwt = token })
    }

    fun getUserById(uuid: UUID) = runBlocking {
        gandlafClient.getUserDataById(getUserDataIdRequest { this.uuid = uuid.toString() })
    }
}