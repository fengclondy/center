package cn.htd.usercenter.data;

@SuppressWarnings("unchecked")
public class AddressLoader {

	// private static final List<ProvinceDTO> pList = new
	// ArrayList<ProvinceDTO>();
	//
	// static {
	// try {
	// // 解析城市Json文件
	// InputStream is =
	// AddressLoader.class.getClassLoader().getResourceAsStream("cities.json");
	// ObjectMapper mapper = new ObjectMapper();
	// List<Map<String, Object>> list = (List<Map<String, Object>>)
	// mapper.readValue(is, List.class);
	// for (Map<String, Object> pInfo : list) {
	// ProvinceDTO tmpP = new ProvinceDTO();
	// tmpP.setCode((String) pInfo.get("code"));
	// tmpP.setName((String) pInfo.get("name"));
	// List<CityDTO> cList = new ArrayList<CityDTO>();
	// List<Map<String, String>> cities = (List<Map<String, String>>)
	// pInfo.get("cities");
	// for (Map<String, String> cInfo : cities) {
	// CityDTO tmpC = new CityDTO();
	// tmpC.setCode(cInfo.get("code"));
	// tmpC.setName(cInfo.get("name"));
	// cList.add(tmpC);
	// }
	// tmpP.setCities(cList);
	// pList.add(tmpP);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static CityDTO getCityName(String cityCode) {
	// for (ProvinceDTO p : pList) {
	// for(CityDTO city : p.getCities()){
	// if (city.getCode().equals(cityCode)) {
	// return city;
	// }
	// }
	// }
	// return null;
	// }
	//
	// public static List<ProvinceDTO> getProvinceList() {
	// return pList;
	// }
	//
	// public static ProvinceDTO getCity(String provinceCode) {
	// for (ProvinceDTO p : pList) {
	// if (p.getCode().equals(provinceCode)) {
	// return p;
	// }
	// }
	// return null;
	// }
}
