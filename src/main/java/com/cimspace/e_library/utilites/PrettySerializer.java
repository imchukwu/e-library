package com.cimspace.e_library.utilites;

import com.fasterxml.jackson.databind.JsonSerializer;

public interface PrettySerializer {
	<T> JsonSerializer<T> getSerializer();
	<T> JsonSerializer<T> getAuditSerializer();
}
