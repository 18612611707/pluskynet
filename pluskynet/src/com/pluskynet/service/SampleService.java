package com.pluskynet.service;

import com.pluskynet.domain.Rule;
import com.pluskynet.domain.Sample;

public interface SampleService {

	void random(Sample sample);

	Sample select();

}
