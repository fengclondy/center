package cn.htd.goodscenter.service.converter;

public interface Converter<T,E> {
	public E convert(T source);
}
