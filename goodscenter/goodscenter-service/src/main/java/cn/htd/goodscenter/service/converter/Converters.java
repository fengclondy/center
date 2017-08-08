package cn.htd.goodscenter.service.converter;

public final class Converters {
	
	public static <SOURCE,TARGET> TARGET convert(SOURCE source,Class<?> t){
		
		String sourceClassName=source.getClass().getSimpleName();
		String targetClassName=t.getSimpleName();
		String converterClassName="cn.htd.goodscenter.service.converter.venus."+sourceClassName+"2"+targetClassName+"Converter";
		
		TARGET result=null;
		
		try {
			Converter<SOURCE, TARGET> converter=(Converter<SOURCE, TARGET>) Class.forName(converterClassName).newInstance();
			result=converter.convert(source);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
