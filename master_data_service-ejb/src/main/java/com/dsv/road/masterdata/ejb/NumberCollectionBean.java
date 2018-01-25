package com.dsv.road.masterdata.ejb;

import com.dsv.road.masterdata.exception.NumberCollectionException;
import com.dsv.road.masterdata.jpa.NumberCollectionManager;
import com.dsv.road.masterdata.model.NumberCollection;
import com.dsv.road.masterdata.model.NumberRange;
import com.dsv.road.shared.masterdata.dto.ActiveState;
import com.dsv.road.shared.masterdata.dto.CheckDigitMethodType;
import com.dsv.road.shared.masterdata.dto.NumberCollectionDto;
import com.dsv.road.shared.masterdata.dto.NumberRangeDto;
import com.dsv.road.shared.masterdata.dto.constraints.MasterDataConstants;
import com.dsv.road.shared.masterdata.exception.ResponseErrorCode;
import com.dsv.shared.dozer.DozerUtility;
import com.dsv.shared.logger.LoggerInterceptor;
import org.dozer.Mapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
@Interceptors(LoggerInterceptor.class)
public class NumberCollectionBean {

    @Inject
    NumberCollectionManager numberCollectionManager;

    Mapper mapper = DozerUtility.getMapper();

    public NumberCollectionDto read(Long id) {
        NumberCollection numberCollection = numberCollectionManager.find(id);
        if (numberCollection == null) {
            return null;
        }
        return mapper.map(numberCollection, NumberCollectionDto.class);
    }

    public void delete(Long id) {
        numberCollectionManager.delete(id);
    }

    public NumberCollectionDto create(NumberCollectionDto numberCollectionDto) {
        if (numberCollectionDto == null) {
            throw new NumberCollectionException(ResponseErrorCode.INSERT_WITH_NULL, "Cannot create null NumberCollectionDto");
        }
        NumberCollection numberCollection = mapper.map(numberCollectionDto, NumberCollection.class);
        return mapper.map(numberCollectionManager.insert(numberCollection), NumberCollectionDto.class);
    }

    public NumberCollectionDto update(Long id, NumberCollectionDto numberCollectionDto) {
        if (numberCollectionDto == null) {
            throw new NumberCollectionException(ResponseErrorCode.UPDATE_WITH_NULL, "Cannot update null NumberCollectionDto");
        }
        NumberCollection numberCollection = mapper.map(numberCollectionDto, NumberCollection.class);
        numberCollection.setId(id);
        return mapper.map(numberCollectionManager.update(numberCollection), NumberCollectionDto.class);
    }

    public List<NumberCollectionDto> search(int first, int pageSize, String filter, MasterDataConstants.DetailLevel format) {
        List<NumberCollection> numberCollections = numberCollectionManager.search(first, pageSize, filter, format);
        List<NumberCollectionDto> numberCollectionDtos = new ArrayList<>();
        for (NumberCollection numberCollection : numberCollections) {
            numberCollectionDtos.add(mapper.map(numberCollection, NumberCollectionDto.class));
        }
        return numberCollectionDtos;
    }

    public NumberRangeDto addToRange(Long id, NumberRangeDto numberRangeDto) {
        if (numberRangeDto == null) {
            throw new NumberCollectionException(ResponseErrorCode.UPDATE_WITH_NULL, "Cannot update null NumberCollectionDto");
        }
        NumberRange numberRange = mapper.map(numberRangeDto, NumberRange.class);
        numberRange.setNumberCollectionId(id);
        return mapper.map(numberCollectionManager.addToRange(numberRange), NumberRangeDto.class);
    }

    public NumberRangeDto readRange(Long id) {
        NumberRange numberRange = numberCollectionManager.findRange(id);
        if (numberRange == null) {
            return null;
        }
        return mapper.map(numberRange, NumberRangeDto.class);
    }

    public NumberRangeDto updateRange(Long id, NumberRangeDto numberRangeDto) {
        if (numberRangeDto == null) {
            throw new NumberCollectionException(ResponseErrorCode.UPDATE_WITH_NULL, "Cannot update null numberRange");
        }
        NumberRange numberCollection = mapper.map(numberRangeDto, NumberRange.class);
        numberCollection.setId(id);
        return mapper.map(numberCollectionManager.updateRange(numberCollection), NumberRangeDto.class);
    }

    public void deleteRange(Long id) {
        numberCollectionManager.deleteRange(id);
    }

    public List<NumberRangeDto> getRangesForCollection(Long id) {
        List<NumberRange> numberRanges = numberCollectionManager.getRangesForCollection(id);
        List<NumberRangeDto> numberRangeDtos = new ArrayList<>();
        for (NumberRange numberRange : numberRanges) {
            numberRangeDtos.add(mapper.map(numberRange, NumberRangeDto.class));
        }
        return numberRangeDtos;
    }

    public boolean exists(Long id) {
        return numberCollectionManager.exists(id);
    }

    public List<String> getNumberList(String name, Long amount, String country, String location) {
        NumberRange range = numberCollectionManager.getCurrentNumberRange(name, country, location);
        int step = range.getStep();
        Long numbersRequired = (amount - 1) * step; //fencepost as the startvalue is not last used.
        ArrayList<String> numbers = generateNumbers(range, numbersRequired);
        if (range.getStartValue() + numbersRequired > range.getMaxValue()) {
            long remainingAmount = range.getStartValue() + (numbersRequired / step) - range.getMaxValue();
            //resets repending on it should cycle
            if (range.getCycle() == 1) {
                Long resetValue = step < 0 ? range.getMaxValue() : range.getMinValue();
                range.setStartValue(resetValue);
            } else {
                Long extremeValue = step > 0 ? range.getMaxValue() : range.getMinValue();
                range.setStartValue(extremeValue + step);
                range.setState(ActiveState.CLOSED);
            }
            numberCollectionManager.updateRange(range, true);
            numbers.addAll(getNumberList(name, remainingAmount, country, location));
        } else {
            range.setStartValue(range.getStartValue() + numbersRequired + step);
            numberCollectionManager.updateRange(range, true);
        }
        return numbers;
    }

    private ArrayList<String> generateNumbers(NumberRange range, Long amount) {
        ArrayList<String> numbers = new ArrayList<>();
        List<String> index = getAllMatches(range.getOutputFormat());
        int lengthOfNumber = calculateLengthOfNumbers(index);
        for (Long i = range.getStartValue(); i <= Math.min(range.getStartValue() + amount, range.getMaxValue()); i = i + range.getStep()) {
            String number = String.format("%0" + lengthOfNumber + "d", i);
            String outPutNumber = range.getOutputFormat();
            for (String s : index) {
                int lengthOfSubNumber = s.length() - 2;
                outPutNumber = outPutNumber.replaceFirst(s, number.substring(0, lengthOfSubNumber));
                number = number.substring(lengthOfSubNumber);
            }
            numbers.add(outPutNumber + getCheckDigit(outPutNumber, range.getCheckDigitMethodType()));
        }
        return numbers;
    }

    private String getCheckDigit(String number, CheckDigitMethodType type) {
        switch (type) {
            case GSIN:
                return "" + getCheckDigitForGTIN(number, 16, 3);
            case SSCC:
                return "" + getCheckDigitForGTIN(number, 17, 3);
            case SIS:
                return "" + getCheckDigitForGTIN(number, 9, 2);
            case NONE:
                return "";
        }
        return "";
    }

    private int getCheckDigitForGTIN(String number, int numberOfRelevantDigits, int factor) {
        boolean timeWith3 = true;
        int sum = 0;
        for (int i = number.length() - 1; i >= number.length() - numberOfRelevantDigits; i--) {
            int valueOfField = Integer.valueOf(number.charAt(i));
            sum += timeWith3 ? valueOfField * factor : valueOfField;
            timeWith3 = !timeWith3;
        }
        int returnValue = 10 - (sum % 10);
        return returnValue % 10 == 0 ? 0 : returnValue;
    }

    public List<String> getAllMatches(String text) {
        List<String> matches = new ArrayList<>();
        Matcher m = Pattern.compile("(\\{#+\\})").matcher(text);
        while (m.find()) {
            matches.add(m.group(1).replace("{", "\\{").replace("}", "\\}"));
        }
        return matches;
    }

    private int calculateLengthOfNumbers(List<String> numberStrings) {
        int sum = 0;
        for (String ints : numberStrings) {
            sum += ints.length() - 2;
        }
        return sum;
    }
}