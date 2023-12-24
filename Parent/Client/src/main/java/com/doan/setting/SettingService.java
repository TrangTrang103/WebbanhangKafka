package com.doan.setting;


import com.doan.mutual.entity.setting.Setting;
import com.doan.mutual.entity.setting.SettingCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;

@Service
public class SettingService {
	@Autowired private SettingRepository settingRepo;
	@Autowired private CurrencyRepository currencyRepo;

	public List<Setting> getGeneralSettings() {
		return settingRepo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.CURRENCY);
	}
	
	public EmailSettingBag getEmailSettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.MAIL_SERVER);
		settings.addAll(settingRepo.findByCategory(SettingCategory.MAIL_TEMPLATES));
		
		return new EmailSettingBag(settings);
	}
	
	public CurrencySettingBag getCurrencySettings() {
		List<Setting> settings = settingRepo.findByCategory(SettingCategory.CURRENCY);
		return new CurrencySettingBag(settings);
	}

}
