package com.github.stanislavbukaevsky.purchasetransporttickets.mapper.record;

import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.Carriers;
import com.github.stanislavbukaevsky.purchasetransporttickets.domain.tables.records.CarriersRecord;
import com.github.stanislavbukaevsky.purchasetransporttickets.model.Carrier;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.springframework.stereotype.Component;

/**
 * Класс-маппер, который преобразует информацию о перевозчике в сущность. <br>
 * Этот класс расширяет интерфейс {@link RecordUnmapper}. Параметры: <br>
 * {@link Carrier} - модель перевозчика <br>
 * {@link CarriersRecord} - сущность перевозчика
 */
@Component
@RequiredArgsConstructor
public class CarrierRecordMapper implements RecordUnmapper<Carrier, CarriersRecord> {
    private final DSLContext dsl;

    /**
     * Этот метод преобразует модель перевозчика в сущность
     *
     * @param carrier модель перевозчика
     * @return Возвращает сформированную сущность перевозчика
     * @throws MappingException исключение, возникающее при сбое запросов связанной службы сопоставления
     */
    @Override
    public @NotNull CarriersRecord unmap(Carrier carrier) throws MappingException {
        CarriersRecord carriersRecord = dsl.newRecord(Carriers.CARRIERS, carrier);
        carriersRecord.setCompanyName(carrier.getCompanyName());
        carriersRecord.setPhoneNumber(carrier.getPhoneNumber());
        return carriersRecord;
    }
}
