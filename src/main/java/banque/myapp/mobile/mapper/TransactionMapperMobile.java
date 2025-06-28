package banque.myapp.mobile.mapper;

import banque.myapp.data.models.entity.Transaction;
import banque.myapp.mobile.dto.request.TransactionRequestDto;
import banque.myapp.mobile.dto.response.TransactionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapperMobile.class)
public interface TransactionMapperMobile {

    @Mapping(source = "envoyeur", target = "envoyeur")
    TransactionResponseDto toTransactionResponseDto(Transaction transaction);

    @Mapping(target = "envoyeur", ignore = true)
    Transaction toTransaction(TransactionRequestDto dto);
}
