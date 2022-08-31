package com.consignment.controller;

import java.text.DecimalFormat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.consignment.model.Consignment;

@RestController
public class ConsignmentController 
{

	@GetMapping("/consignment")
	public String saveEmp(@RequestBody Consignment consignment )
	{
		String prefix = "FMCC";
		
		int digit 			= consignment.getDigits();
		int lastUsedIndex 	= consignment.getLastUsedIndex();
		int rangeStart  	= consignment.getRangeStart();
		int rangeEnd  		= consignment.getRangeEnd();
		int nextUsedIndex 	= lastUsedIndex + 1;
		int sumEveryFirst 	= 0;
		int sumEverySecond 	= 0;
		String paddedFormat	= "";  
		
		if(nextUsedIndex > rangeEnd ||  nextUsedIndex < rangeStart)
	    	return "Used Index is out of range";
	      
		for (int i = 0; i<digit; i++)
			paddedFormat  = paddedFormat +"0";

		DecimalFormat decimalFormat = new DecimalFormat(paddedFormat);                
	    String digitValue= decimalFormat.format(nextUsedIndex);
	    char[] revesedigit = digitValue.toCharArray();

		for (int i = revesedigit.length-1; i >= 0; i--)
		{
			if((revesedigit.length-i)%2 == 0)
				sumEverySecond  = sumEverySecond + Character.getNumericValue(revesedigit[i]);
			else
				sumEveryFirst  = sumEveryFirst + Character.getNumericValue(revesedigit[i]);
		}
		
        int checksum = 100 - (sumEveryFirst *3) - (sumEverySecond *7);
        String output = prefix + consignment.getAccountNumber()+digitValue +checksum;
        
        return "Output : "+output;
	}
	
}
