package com.pluskynet.service;

import com.pluskynet.domain.Sample;
import com.pluskynet.domain.User;

public interface SampleService {

	void random(Sample sample, User user);

	Sample select(User user);

}
