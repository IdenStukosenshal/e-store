package e_store.mappers;

public interface MapperIntrf<From, To> {
    To map(From obj);

    default To mapUpd(From dto, To entity){
        return entity;
    }
}
