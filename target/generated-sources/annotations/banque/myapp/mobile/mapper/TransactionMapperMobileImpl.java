package banque.myapp.mobile.mapper;

import banque.myapp.data.models.entity.Transaction;
import banque.myapp.mobile.dto.request.TransactionRequestDto;
import banque.myapp.mobile.dto.response.TransactionResponseDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-24T21:39:31+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class TransactionMapperMobileImpl implements TransactionMapperMobile {

    @Autowired
    private UserMapperMobile userMapperMobile;

    @Override
    public TransactionResponseDto toTransactionResponseDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();

        transactionResponseDto.setEnvoyeur( userMapperMobile.toUserResponseDto( transaction.getEnvoyeur() ) );
        transactionResponseDto.setId( transaction.getId() );
        transactionResponseDto.setMontant( transaction.getMontant() );
        transactionResponseDto.setReceveur( userMapperMobile.toUserResponseDto( transaction.getReceveur() ) );
        transactionResponseDto.setDate( transaction.getDate() );
        transactionResponseDto.setType( transaction.getType() );

        return transactionResponseDto;
    }

    @Override
    public Transaction toTransaction(TransactionRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setMontant( dto.getMontant() );
        transaction.setDate( dto.getDate() );

        return transaction;
    }
}
