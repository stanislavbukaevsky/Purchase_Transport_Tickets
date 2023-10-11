package com.github.stanislavbukaevsky.purchasetransporttickets.repository;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Carriers;
import com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record.CarrierRecordMapper;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Carrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.springframework.stereotype.Repository;

import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.ExceptionTextMessageConstant.DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY;
import static com.github.stanislavbukaevsky.purchasetransporttickets.constant.LoggerTextMessageConstant.*;

/**
 * Класс-репозиторий, для связи с базой данных и вытягивания из нее информации о перевозчике
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class CarrierRepository {
    private final DSLContext dsl;
    private final CarrierRecordMapper carrierRecordMapper;

    /**
     * Этот метод сохраняет информацию о перевозчике в базу данных
     *
     * @param carrier модель перевозчика
     * @return Возвращает модель перевозчика
     */
    public Carrier save(Carrier carrier) {
        log.info(SAVE_CARRIER_MESSAGE_LOGGER_REPOSITORY, carrier);
        return dsl.insertInto(Carriers.CARRIERS)
                .set(carrierRecordMapper.unmap(carrier))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + carrier))
                .into(Carrier.class);
    }

    /**
     * Этот метод изменяет и сохраняет информацию о перевозчике в базе данных
     *
     * @param carrier модель перевозчика
     * @return Возвращает модель перевозчика
     */
    public Carrier update(Carrier carrier) {
        log.info(UPDATE_CARRIER_MESSAGE_LOGGER_REPOSITORY, carrier);
        return dsl.update(Carriers.CARRIERS)
                .set(carrierRecordMapper.unmap(carrier))
                .where(Carriers.CARRIERS.ID.eq(carrier.getId()))
                .returning()
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_MESSAGE_REPOSITORY + carrier))
                .into(Carrier.class);
    }

    /**
     * Этот метод ищет перевозчика в базе данных по его уникальному идентификатору
     *
     * @param id уникальный идентификатор перевозчика
     * @return Возвращает модель найденного перевозчика
     */
    public Carrier findCarrierById(Long id) {
        log.info(FIND_CARRIER_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        return dsl.select()
                .from(Carriers.CARRIERS)
                .where(Carriers.CARRIERS.ID.eq(id))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + id))
                .into(Carrier.class);
    }

    /**
     * Этот метод ищет перевозчика в базе данных по названию компании
     *
     * @param companyName компания перевозчика
     * @return Возвращает модель найденного перевозчика
     */
    public Carrier findCarrierByCompanyName(String companyName) {
        String like = "%" + companyName + "%";
        log.info(FIND_CARRIER_BY_COMPANY_NAME_MESSAGE_LOGGER_REPOSITORY, companyName);
        return dsl.select()
                .from(Carriers.CARRIERS)
                .where(Carriers.CARRIERS.COMPANY_NAME.likeIgnoreCase(like))
                .fetchOptional()
                .orElseThrow(() -> new DataAccessException(DATA_ACCESS_EXCEPTION_FIND_MESSAGE_REPOSITORY + companyName))
                .into(Carrier.class);
    }

    /**
     * Этот метод удаляет перевозчика из базы данных по его уникальному идентификатору
     *
     * @param id уникальный идентификатор перевозчика
     */
    public void deleteById(Long id) {
        log.info(DELETE_CARRIER_BY_ID_MESSAGE_LOGGER_REPOSITORY, id);
        dsl.deleteFrom(Carriers.CARRIERS)
                .where(Carriers.CARRIERS.ID.eq(id))
                .execute();
    }
}
