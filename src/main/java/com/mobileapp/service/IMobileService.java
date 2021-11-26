package com.mobileapp.service;

import java.util.List;
import com.mobileapp.exception.MobileNotFoundException;
import com.mobileapp.model.Mobile;

//mock this interface
public interface IMobileService {

	// return null, exception , new Mobile(), mobile
	Mobile getById(int id) throws MobileNotFoundException;

	// return null, empty arratList, list of mobiles, exception
	List<Mobile> getByBrand(String brand) throws MobileNotFoundException;

}
