package com.cqyang.demo.crawler.medical;

import com.alibaba.fastjson.JSON;
import com.cqyang.demo.crawler.medical.model.FixedHospitalRequest;
import com.cqyang.demo.crawler.medical.model.MedicalEncryptRequest;
import org.junit.jupiter.api.Test;

import javax.script.ScriptException;

public class EncryptUtilTest {

    @Test
    public void testGetHeader() throws ScriptException {
        System.out.println(JSON.toJSONString(EncryptUtil.getHeader()));
    }

    @Test
    public void testEncryptFixedHospital() throws ScriptException {
        String json = "{\"addr\":\"\",\"regnCode\":\"110000\",\"medinsName\":\"\",\"sprtEcFlag\":\"\",\"medinsLvCode\":\"\",\"medinsTypeCode\":\"\",\"pageNum\":1,\"pageSize\":10}";
        FixedHospitalRequest fixedHospitalRequest = JSON.parseObject(json, FixedHospitalRequest.class);
        MedicalEncryptRequest medicalEncryptRequest = EncryptUtil.encryptFixedHospital(fixedHospitalRequest);
        System.out.println(JSON.toJSONString(medicalEncryptRequest));
    }

    @Test
    public void getFixedHospitalRequest() throws ScriptException {
        testGetHeader();
        testEncryptFixedHospital();
    }

//    @Test
//    public void testDecrypt() throws ScriptException {
//        String json = "{\"code\":0,\"data\":{\"signData\":\"876L4VHqQNebAH0+vmKsR65NQ+pFVG4RrTbq4pEysbChVFrmVtCGbztp6Bjz8IWdZMggCxYgSt5kpAChhAFRIw==\",\"encType\":\"SM4\",\"data\":{\"encData\":\"943195DDA5E5337E08E0A527071CC637E0074CBD73D293A3643B2199C79561872E19ABE7BB2600D23E9CDB14C1A75194C1D3C735D2DCC50E297311A423B72BF0AE4781F770874C1AC923AD26618311F171D4512A975655097D4F91564D7CD43AFFCE95FB486F6F78D90963A742D4558DBC493DD97CA173A89F91427EDB566E9D80F620CEF65F3F1744412C847004246F1D71108A88E4C6255FFCA2E6849D99913DAC47974D7BC90C710F376C15A760EB2BA8645F994EBF0B8209BD1DA6CFFD8666C2D9F16575827602372F602E8A7A1B84BD58BBE340653BD3C4FB91F65CCB60889EDDECC3D9AB9B3FB58E824F4038973A8DA722BAA9BABA3FCEF0CF95F8A1BFDA6C826763607FEFEDAB1309CB006075AE8E05F6818745764B703C960A7F112E54357FD76F66B0027B794C39FC6503D338057FEE0625CD4B4C25A29B14608D76605AE0AB58C7DABD0BE28A0A7E993E4C7F9972D43E0F34ECA77A93292AA79856E98BDFDF2A4BB1A1E84D8EBAC3B298B4CA2D001DF929AFBA8F663BC134C42C85A724A620BA4C4C3613BB89C2B5E6FCF47963BB6D24A1AFE097F71D9449D7D96DAEBDCCC970B2CDB20F7EF3DDBB68A8FA057D45914722AAA7BDBB8E5D8A89B5347F542DEDF778BFDE731F07FD7DD5260533B7EAC89841CDAFDB41FA55A68425F44392ADCF0301B5D9A74A762F35692A1B4240D64C8784217F395FA129834F03837C7857A64ED7FEE9A7686980E8B2342F98C0E32113F89500E0B89FF3B597143760B49AE96307FFC0AE50A269AA81FCBB2DF9E141F8801B14196DF75FD4B78974AB01C71831AD2646B5D28F17F6A5A34EB6FAD16335399130CCDC6A710401DFE6C6CFA9061A2F2966B8C42F35868A601F896BC67533151C3D7D2C8704B297F9351AFCDB1F6BF97E3D700198FAF5BBAFB45F94CB3C161405ED009E6A7101BD4DA52140B76D653223FC0864A7365FABD82F89A617004F55E93D9F332525D0BC9011BBCDB23C28778180670827CD3E847963E5E4A18AE49B4D40AADB7B625DB313A5AA8E5B2739E2F8A190BE2F8899C528D4F8382C3EC6841D16AA1F88BEF25FF3632A623F91CBF1D5162F9E7D154CB392FB1B8A6F4994579B4694498448828AA8CA11AAF12D40A6B62BF3F76DBCA0924877E1F13A205F36D94965AF33C6A3CB73091FA8D5696200B41E9DAF8BA69E5FF5543CD2135BB506927C07E64E240632CA8A4740D00FC1F3029D56DC4045A6D6ECC7EDD081A4DFFF947CAD78F12993DFF99DF297355592BCB7B649D8F3605F0DC0A9FCD3DBD9C090A41D63BFA74FD4F73530D0ABE1B4D04A96A61DF56177F7AA12F41DDE087A058C17218AFDA3E16EE5964A98070B0160B2A19B32E913D7BA20375ACC813E5A3BC90477684F3AC3ABB86E130ABF31AFBFEDC77978D525EF899166C1A3F712410D40A97CB2B2BBD27E83C14BD03D1D4E2FB7DF972493679EF395559B8D3D911AE35353D571D362995A1E8A63064B999D5EDCED26565F98A67EADD6D6B2F6BD5BA5A378B16D832AA8B4985E7DF3C7B7C34260E84FFDA315D530DD0F498A5506333CC2494C39FC985C0C8588B3FF60B8E54EAC0DB03AA6525C6A12F65126390739D8F60C81265A400D776E39C97D015F7B29138EB2C5A2B0C45C71013E6CECAA7CA214B4B56995E35E99806CC63743057C6A3CB7BF92B8FB52FC731960D2F6A6C6C79B420FD9B8FBC0156A4335F74F2EC62B070D342EF14CA67ED33A50F79DFC39F7FC8E5F1A43C429EB133EA4C0BD4E3637F7981C7B8380C8D7B340138157E4F8B5F5ABD3F595F4677DB4D1F175D50D863BBDAEFEBA988EA52A675ADF08EEE39EE273B98F930BFCD3DC647FD97586104E7D8973CF56609BAB7DA019354F2CECF59231FA20DD805FBE5F1C6E9E4240D64C8784217F395FA129834F03837C7857A64ED7FEE9A7686980E8B2342FE841B5F319461D357752F998C515230308876FF58D6290EA65B6AC6D5A13EE319D3F9A6A7761254178DAB215456B459C447080B0FF2B509DF7B40533108C8C0CD23C4A3DA35DA0E5AB7DC248959391DB18EC6F9A44A974D9B23C10556F766BB7200162303EC72ABC55B1B0A9F40A4ECB21D497EC408571F498877C32A22E823073F17C02BEA8959F2425862E78F063A67FCCDBBB34FF1A82149CD277D2836B074941E60332022189140AEDEFDB0B240FC6575CDD0C9569F0C7A0C6EBB7584657B238A6B35E1D071F281330524D7A1E0B924B62AF6762FAED336B88E6368A6E9DB86C96F6FB2671C9659E3BF486FD8CF1B55BB9F642F90906A2BB1B3B152C10CB190FAD35610381D382D8ACB38D68B57592F1A56565EF8C93B33247BF3D7BA706771C8F91AE5ACC740B57916167F2D23C75914B3388ABC6AD13DE783B8CA7C86028F48BFC4C6E0B47063E1B7B1A8BD639C5178654881B8599A3D8F2B6A9AC7E765B6EABA21AFF6765BB4EEBA4DCC3EADB6F16E6C3EB80E734D282ADC53208BE40193EF9F1C38632DA9943EEE7B4D2DA34E9E9DFCC3555453A28D11FD63B559614BB87EDA5BF77762503A80AF4895B20EDE16170CB4D150B0A1546564776F232C836D549352832A6D13538B5FF9B860933D4DCC49EA3F22A0FF0B916F471FB060D63C2220FF9368C5EE27F187F051AAFAA853F05DD67289B4AC5F3767326B7276892DA07444DA1526331109FAA4B5190B5FAE6790BA5AB6A4169B4E3152D74BDDDB85B44D5FAA82924681853EF4A58F7A280B916122DC65301BDABDF7D7C16694AACD7AEF7C03B19987075E165538BA52419162EEB82E1BACC59F41123F438F50C8A9F7F301699C4C04071392D8250F8C08246267ED47315B30691B2C1F7253C6D098C2083F2F323FCB1FB861BAA6DFF83A82A5D53E341787CC1FA27D3832D7A150F0D82A7ADACC13EC53F2804BDDF0347D95659650003E7ED89A3EB6F3461113177535DFA261EE995A2FDC6E9DCC04A4BB14F8E47C40D2D49B9312649FB5725868FCC5A373E4C2CB2CBF10CCEE7874FAD02B2793D90BDA76D0CCF74FA1661A86CD07703A34185B8A86D41D680040692B1A5AB06F9A6981B5588024181B748493B7B501B03E0A9D5524EF1E8BC4BAC8C8EE20335C516E712123727637E87ABF1CADCB35A83E4C9E4F33E9681D786F3935EAF5BA6A605A93C2381DC04C4F377F60ACB8246672AB73B4F84F2445787FE548CD156CF54C5BABCA6BB4D225236B97D06B1429E2B8381FE96D6CD6D730C8D110E40D986C551054BC1EE99E0D9536C19A8091759A183C1B267EC6AE5FDF501064E893CB0DBC651DB8BA90E89AC2043B4EE7EDEC44FB4A70191CA56B581D23254D508DCFB0F5D40EF0F18AC8126B0B2A3AF8E673DCB0870F4AD2DB880860D370F55D06CD2BA4F68F897676C6C92982DBA65BC3EFFB0AC7992C5286D9DF63EF8DBD554357FD76F66B0027B794C39FC6503D386DC970915C56AF82B5C76F73C752D1291E417969913064CE1FC21F535A72F3C74F75E8ECC5D47B74FE5C77A3529CFEABB7D799C62EE07CBFE84CA57D7995336C5BE1E4B789DC8DF5E0D05B16E8AC0E5AC5BAC60166462897D790195F7C0A6BF192727ED8117A3FD4B2A1F6163E8B2ECEC902125FDB8065C2EE29CBEDFC0F35B1C36590BBFFA10257AEEB86BBA643B62244B2CC06230D4502D3C36F68508C97E51216029CB77CAF26C01D71AA235FED8CBD123FF2112EB482F32B2B8D8992D4985037AD1F71DE081283C4ADEBD9FAC7B8BFFED45876F7EAAE2F66F93DB88ACBE767B76E759F96B803566BA0E2FAB08B3F079C5DD4BD4C9E0B0FFF132058CA6F3AC461CB02F1326A245AF1E5BA97F37292BA8645F994EBF0B8209BD1DA6CFFD8666C2D9F16575827602372F602E8A7A1B84BD58BBE340653BD3C4FB91F65CCB606EB7DC60E2802BA61D77518F1A300CEE2264983D72FB084B6AE39777210C81FEF1E292DDCA941CA5C1C287C6E634A0F0520ED66277A3376EA0102E7DF0F88808924B62AF6762FAED336B88E6368A6E9D1CF6D5903C24B25FA7FFE22D8A9FC67535EF4D89DECB7A5CA806E6A159A8CB5BBC7B032EE60E32746D556A34EF82FBF55841A0B1104D57C6DDA4BCC210DD7B607F3C3E7A347CC2CE8E8F2A55A8E044C175914B3388ABC6AD13DE783B8CA7C8601923CC581F07E2E145C20CBE3DF1FC4982ECC6095D90DDB109417BC377F92E6539F557D6075BEC949E584487FCAACDF3293950EBABB715C15539BA1FD8996C544D0D4E0D8A2F99185006C6576EB8B2D7C5985746F20552902911B4C33ABFC61A11D12030A7776174CBC0E1B840FA690CB6C854BA060256C100D31474DA29098F9F91DE59EE6BDE8ADC42DE381A56D478BC64AFBF148EAB67857929087F611266D91B5EE8ACD3F012A34020D8DC908F7EFC9207578DC7C41886C402A407C0B64C61CBA83CE55E9E09C4920BA4BFECD078DF62C8F6B21946793B2A1B9D5FFC4ADDC4A7F6590E7AFC8B14A7BA41E958E631FD9A9384A3030FEB1F8A3ABE14198CF89971179EC38B20C5C50D4BC3E40AE586937BE861ED32AFF2F8052E6F15DF560867F44F5418E7249951DD6EBEFA233AD1098C2083F2F323FCB1FB861BAA6DFF8375F7184DE14CAE8881D797D762CDD179904A2DEFB5FA5869D3601E97A9F21AD1F7415342CEA807E946E1CD99AE6096FCD62B808CE7C815D8623D4DC8C8F47331B9FAD7CC239E35538657A66CF301EF0A63E33379C5E75BB400BEF1E79434C68FF201E166BF0CB4B5CC87B3B03559115D84BF05FACB5B872ABF86F9E043F6DB7F1CF5446DE088C25FF4A158469CD4BD36A720DB69B8522EA886A6A86D070A31B5F617498F276CA11C788BA75F4714E9D3FCD3DBD9C090A41D63BFA74FD4F735306260CFF1857B23F54E9C707B929D1BFDB94216035C335F1473E7C702E3E272BA85691761F12307EFB8EC17C4BB862B45CC813E5A3BC90477684F3AC3ABB86E13BC72C529EE6699ADAF6B703BB034730DA3F712410D40A97CB2B2BBD27E83C14BD03D1D4E2FB7DF972493679EF395559B8D3D911AE35353D571D362995A1E8A63DD598EAA07DFA8F82049F6C238E99D5C257E468AB94A570FC011A9D3479B10E1C454D65AF7874C0FD9331E35EA365B2EA50441116416AC351A9E5F5D214AA520C09EA34F639DE32FC984C766470786EE10142A8A72BBF32487FB7A380CCD65298843C7F029595DC74AF0530032EBEE29190FAD35610381D382D8ACB38D68B575FF757728A5F2FD5CD949850555658541F79DFC39F7FC8E5F1A43C429EB133EA4D29DDE2FF179916440F8EA4022D2F805C0A29E8E97BD1B504C3D7F265DB75E36BE22767CE2B8F0BD181BE14C0F5DE170BAD4E671094483B6F8469145D81E515B81CCE9ED3FCC33A7A44DC5157C01546F85037AD1F71DE081283C4ADEBD9FAC7B0F254B84F80161D38D596625EA40F35F3CA05C36FC690DD68763389428229905AA7DCDA83ED51A55A806C65E676949A51D71108A88E4C6255FFCA2E6849D999123084363E364E78C53EFE40ACAD5A4EE2BA8645F994EBF0B8209BD1DA6CFFD8666C2D9F16575827602372F602E8A7A1B84BD58BBE340653BD3C4FB91F65CCB606EB7DC60E2802BA61D77518F1A300CEE2264983D72FB084B6AE39777210C81FEF1E292DDCA941CA5C1C287C6E634A0F0520ED66277A3376EA0102E7DF0F88808924B62AF6762FAED336B88E6368A6E9D1CF6D5903C24B25FA7FFE22D8A9FC67535EF4D89DECB7A5CA806E6A159A8CB5BBC7B032EE60E32746D556A34EF82FBF5FE3C6C88E73FE14CE79BA0B4A41A44C01653241283D971701D2C15884C73EB6B7ABDC771224B2FC5F90E8FB1A5D6311D7157D31BE9294B17B0439AC275664E055A0AD99EA3130AC85C4027A84CFC67EEE0AE2ACEF1F6D0397C849A4EE344066DED15CD6FB132138048FA64C13EC1D282C1AB40CDA1ED0A69CB51509C5891C0F6720E6812453B72DCA4AE6443703CDCF724771FDDAC9F84A1E89C9D5B654D33A4F6DBDB733BE6A7942CCCB43DDCF5929AF01AC2D2DA7BEBA1059CAEF478D1A91AA5A33219818E56A770DE47A12DBA68D5D39ED2A48BA0FA7CFF5035A4B923E36D9818939BBDAADF7958E4C14B413B500DD2CC8CF18DFF51CF09A57D8A9D04DBE79BF03B1A026B81C765A2CF29FC48C6D5F93B3E281D16BC168C01A73ECCDBCB1A2F44966BC68BE5E09791A57E772FC55FCC57079672E25ECC6E056EAF4564007F\"},\"signType\":\"SM2\",\"appCode\":\"T98HPCGN5ZVVQBS8LZQNOAEXVI9GYHKQ\",\"version\":\"1.0.0\",\"timestamp\":\"1711803781784\"},\"message\":\"成功\",\"timestamp\":\"1711803781\",\"type\":\"success\"}";
//        MedicalEncryptResponse medicalEncryptResponse = JSON.parseObject(json, MedicalEncryptResponse.class);
//        MedicalPageResponse medicalPageResponse = EncryptUtil.decrypt(medicalEncryptResponse);
//        System.out.println(JSON.toJSONString(medicalPageResponse));
//    }
}

