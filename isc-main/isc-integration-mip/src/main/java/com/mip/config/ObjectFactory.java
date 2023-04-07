
package com.mip.config;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.mip.config package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ArgumentExceptionFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "ArgumentExceptionFault");
    private final static QName _ArgumentNullExceptionFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "ArgumentNullExceptionFault");
    private final static QName _LicenseExceptionFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "LicenseExceptionFault");
    private final static QName _NotSupportedExceptionFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "NotSupportedExceptionFault");
    private final static QName _PathNotFoundExceptionFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "PathNotFoundExceptionFault");
    private final static QName _UnauthorizedAccessFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "UnauthorizedAccessFault");
    private final static QName _ServerExceptionFault_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", "ServerExceptionFault");
    private final static QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
    private final static QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
    private final static QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
    private final static QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
    private final static QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
    private final static QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
    private final static QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
    private final static QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
    private final static QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
    private final static QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
    private final static QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
    private final static QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
    private final static QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
    private final static QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
    private final static QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
    private final static QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
    private final static QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
    private final static QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
    private final static QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
    private final static QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
    private final static QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    private final static QName _ConfigurationItem_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ConfigurationItem");
    private final static QName _ArrayOfConfigurationItem_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfConfigurationItem");
    private final static QName _EnablePropertyInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "EnablePropertyInfo");
    private final static QName _ArrayOfProperty_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfProperty");
    private final static QName _Property_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "Property");
    private final static QName _ArrayOfValueTypeInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfValueTypeInfo");
    private final static QName _ValueTypeInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ValueTypeInfo");
    private final static QName _ItemTypes_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ItemTypes");
    private final static QName _ValidateResult_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ValidateResult");
    private final static QName _ArrayOfErrorResult_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfErrorResult");
    private final static QName _ErrorResult_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ErrorResult");
    private final static QName _ArrayOfMethodInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfMethodInfo");
    private final static QName _MethodInfo_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "MethodInfo");
    private final static QName _ArrayOfItemFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfItemFilter");
    private final static QName _ItemFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ItemFilter");
    private final static QName _EnableFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "EnableFilter");
    private final static QName _ArrayOfPropertyFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "ArrayOfPropertyFilter");
    private final static QName _PropertyFilter_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "PropertyFilter");
    private final static QName _Operator_QNAME = new QName("http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", "Operator");
    private final static QName _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    private final static QName _ArrayOfKeyValueOfstringstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfKeyValueOfstringstring");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.mip.config
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfstringstring }
     * 
     */
    public ArrayOfKeyValueOfstringstring createArrayOfKeyValueOfstringstring() {
        return new ArrayOfKeyValueOfstringstring();
    }

    /**
     * Create an instance of {@link ArgumentExceptionFault }
     * 
     */
    public ArgumentExceptionFault createArgumentExceptionFault() {
        return new ArgumentExceptionFault();
    }

    /**
     * Create an instance of {@link ArgumentNullExceptionFault }
     * 
     */
    public ArgumentNullExceptionFault createArgumentNullExceptionFault() {
        return new ArgumentNullExceptionFault();
    }

    /**
     * Create an instance of {@link LicenseExceptionFault }
     * 
     */
    public LicenseExceptionFault createLicenseExceptionFault() {
        return new LicenseExceptionFault();
    }

    /**
     * Create an instance of {@link NotSupportedExceptionFault }
     * 
     */
    public NotSupportedExceptionFault createNotSupportedExceptionFault() {
        return new NotSupportedExceptionFault();
    }

    /**
     * Create an instance of {@link PathNotFoundExceptionFault }
     * 
     */
    public PathNotFoundExceptionFault createPathNotFoundExceptionFault() {
        return new PathNotFoundExceptionFault();
    }

    /**
     * Create an instance of {@link UnauthorizedAccessFault }
     * 
     */
    public UnauthorizedAccessFault createUnauthorizedAccessFault() {
        return new UnauthorizedAccessFault();
    }

    /**
     * Create an instance of {@link ServerExceptionFault }
     * 
     */
    public ServerExceptionFault createServerExceptionFault() {
        return new ServerExceptionFault();
    }

    /**
     * Create an instance of {@link ConfigurationItem }
     * 
     */
    public ConfigurationItem createConfigurationItem() {
        return new ConfigurationItem();
    }

    /**
     * Create an instance of {@link ArrayOfConfigurationItem }
     * 
     */
    public ArrayOfConfigurationItem createArrayOfConfigurationItem() {
        return new ArrayOfConfigurationItem();
    }

    /**
     * Create an instance of {@link EnablePropertyInfo }
     * 
     */
    public EnablePropertyInfo createEnablePropertyInfo() {
        return new EnablePropertyInfo();
    }

    /**
     * Create an instance of {@link ArrayOfProperty }
     * 
     */
    public ArrayOfProperty createArrayOfProperty() {
        return new ArrayOfProperty();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link ArrayOfValueTypeInfo }
     * 
     */
    public ArrayOfValueTypeInfo createArrayOfValueTypeInfo() {
        return new ArrayOfValueTypeInfo();
    }

    /**
     * Create an instance of {@link ValueTypeInfo }
     * 
     */
    public ValueTypeInfo createValueTypeInfo() {
        return new ValueTypeInfo();
    }

    /**
     * Create an instance of {@link ItemTypes }
     * 
     */
    public ItemTypes createItemTypes() {
        return new ItemTypes();
    }

    /**
     * Create an instance of {@link ValidateResult }
     * 
     */
    public ValidateResult createValidateResult() {
        return new ValidateResult();
    }

    /**
     * Create an instance of {@link ArrayOfErrorResult }
     * 
     */
    public ArrayOfErrorResult createArrayOfErrorResult() {
        return new ArrayOfErrorResult();
    }

    /**
     * Create an instance of {@link ErrorResult }
     * 
     */
    public ErrorResult createErrorResult() {
        return new ErrorResult();
    }

    /**
     * Create an instance of {@link ArrayOfMethodInfo }
     * 
     */
    public ArrayOfMethodInfo createArrayOfMethodInfo() {
        return new ArrayOfMethodInfo();
    }

    /**
     * Create an instance of {@link MethodInfo }
     * 
     */
    public MethodInfo createMethodInfo() {
        return new MethodInfo();
    }

    /**
     * Create an instance of {@link ArrayOfItemFilter }
     * 
     */
    public ArrayOfItemFilter createArrayOfItemFilter() {
        return new ArrayOfItemFilter();
    }

    /**
     * Create an instance of {@link ItemFilter }
     * 
     */
    public ItemFilter createItemFilter() {
        return new ItemFilter();
    }

    /**
     * Create an instance of {@link ArrayOfPropertyFilter }
     * 
     */
    public ArrayOfPropertyFilter createArrayOfPropertyFilter() {
        return new ArrayOfPropertyFilter();
    }

    /**
     * Create an instance of {@link PropertyFilter }
     * 
     */
    public PropertyFilter createPropertyFilter() {
        return new PropertyFilter();
    }

    /**
     * Create an instance of {@link ArrayOfstring }
     * 
     */
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }

    /**
     * Create an instance of {@link GetItem }
     * 
     */
    public GetItem createGetItem() {
        return new GetItem();
    }

    /**
     * Create an instance of {@link GetItemResponse }
     * 
     */
    public GetItemResponse createGetItemResponse() {
        return new GetItemResponse();
    }

    /**
     * Create an instance of {@link GetItems }
     * 
     */
    public GetItems createGetItems() {
        return new GetItems();
    }

    /**
     * Create an instance of {@link GetItemsResponse }
     * 
     */
    public GetItemsResponse createGetItemsResponse() {
        return new GetItemsResponse();
    }

    /**
     * Create an instance of {@link SetItem }
     * 
     */
    public SetItem createSetItem() {
        return new SetItem();
    }

    /**
     * Create an instance of {@link SetItemResponse }
     * 
     */
    public SetItemResponse createSetItemResponse() {
        return new SetItemResponse();
    }

    /**
     * Create an instance of {@link HasChildItems }
     * 
     */
    public HasChildItems createHasChildItems() {
        return new HasChildItems();
    }

    /**
     * Create an instance of {@link HasChildItemsResponse }
     * 
     */
    public HasChildItemsResponse createHasChildItemsResponse() {
        return new HasChildItemsResponse();
    }

    /**
     * Create an instance of {@link GetChildItems }
     * 
     */
    public GetChildItems createGetChildItems() {
        return new GetChildItems();
    }

    /**
     * Create an instance of {@link GetChildItemsResponse }
     * 
     */
    public GetChildItemsResponse createGetChildItemsResponse() {
        return new GetChildItemsResponse();
    }

    /**
     * Create an instance of {@link ConstructItem }
     * 
     */
    public ConstructItem createConstructItem() {
        return new ConstructItem();
    }

    /**
     * Create an instance of {@link ConstructItemResponse }
     * 
     */
    public ConstructItemResponse createConstructItemResponse() {
        return new ConstructItemResponse();
    }

    /**
     * Create an instance of {@link ConstructChildItem }
     * 
     */
    public ConstructChildItem createConstructChildItem() {
        return new ConstructChildItem();
    }

    /**
     * Create an instance of {@link ConstructChildItemResponse }
     * 
     */
    public ConstructChildItemResponse createConstructChildItemResponse() {
        return new ConstructChildItemResponse();
    }

    /**
     * Create an instance of {@link ValidateItem }
     * 
     */
    public ValidateItem createValidateItem() {
        return new ValidateItem();
    }

    /**
     * Create an instance of {@link ValidateItemResponse }
     * 
     */
    public ValidateItemResponse createValidateItemResponse() {
        return new ValidateItemResponse();
    }

    /**
     * Create an instance of {@link DeleteItem }
     * 
     */
    public DeleteItem createDeleteItem() {
        return new DeleteItem();
    }

    /**
     * Create an instance of {@link DeleteItemResponse }
     * 
     */
    public DeleteItemResponse createDeleteItemResponse() {
        return new DeleteItemResponse();
    }

    /**
     * Create an instance of {@link GetDependantItems }
     * 
     */
    public GetDependantItems createGetDependantItems() {
        return new GetDependantItems();
    }

    /**
     * Create an instance of {@link GetDependantItemsResponse }
     * 
     */
    public GetDependantItemsResponse createGetDependantItemsResponse() {
        return new GetDependantItemsResponse();
    }

    /**
     * Create an instance of {@link GetMethodInfos }
     * 
     */
    public GetMethodInfos createGetMethodInfos() {
        return new GetMethodInfos();
    }

    /**
     * Create an instance of {@link GetMethodInfosResponse }
     * 
     */
    public GetMethodInfosResponse createGetMethodInfosResponse() {
        return new GetMethodInfosResponse();
    }

    /**
     * Create an instance of {@link GetMethodInfo }
     * 
     */
    public GetMethodInfo createGetMethodInfo() {
        return new GetMethodInfo();
    }

    /**
     * Create an instance of {@link GetMethodInfoResponse }
     * 
     */
    public GetMethodInfoResponse createGetMethodInfoResponse() {
        return new GetMethodInfoResponse();
    }

    /**
     * Create an instance of {@link InvokeMethod }
     * 
     */
    public InvokeMethod createInvokeMethod() {
        return new InvokeMethod();
    }

    /**
     * Create an instance of {@link InvokeMethodResponse }
     * 
     */
    public InvokeMethodResponse createInvokeMethodResponse() {
        return new InvokeMethodResponse();
    }

    /**
     * Create an instance of {@link GetTranslations }
     * 
     */
    public GetTranslations createGetTranslations() {
        return new GetTranslations();
    }

    /**
     * Create an instance of {@link GetTranslationsResponse }
     * 
     */
    public GetTranslationsResponse createGetTranslationsResponse() {
        return new GetTranslationsResponse();
    }

    /**
     * Create an instance of {@link GetChildItemsHierarchy }
     * 
     */
    public GetChildItemsHierarchy createGetChildItemsHierarchy() {
        return new GetChildItemsHierarchy();
    }

    /**
     * Create an instance of {@link GetChildItemsHierarchyResponse }
     * 
     */
    public GetChildItemsHierarchyResponse createGetChildItemsHierarchyResponse() {
        return new GetChildItemsHierarchyResponse();
    }

    /**
     * Create an instance of {@link QueryItems }
     * 
     */
    public QueryItems createQueryItems() {
        return new QueryItems();
    }

    /**
     * Create an instance of {@link QueryItemsResponse }
     * 
     */
    public QueryItemsResponse createQueryItemsResponse() {
        return new QueryItemsResponse();
    }

    /**
     * Create an instance of {@link ArrayOfKeyValueOfstringstring.KeyValueOfstringstring }
     * 
     */
    public ArrayOfKeyValueOfstringstring.KeyValueOfstringstring createArrayOfKeyValueOfstringstringKeyValueOfstringstring() {
        return new ArrayOfKeyValueOfstringstring.KeyValueOfstringstring();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArgumentExceptionFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArgumentExceptionFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "ArgumentExceptionFault")
    public JAXBElement<ArgumentExceptionFault> createArgumentExceptionFault(ArgumentExceptionFault value) {
        return new JAXBElement<ArgumentExceptionFault>(_ArgumentExceptionFault_QNAME, ArgumentExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArgumentNullExceptionFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArgumentNullExceptionFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "ArgumentNullExceptionFault")
    public JAXBElement<ArgumentNullExceptionFault> createArgumentNullExceptionFault(ArgumentNullExceptionFault value) {
        return new JAXBElement<ArgumentNullExceptionFault>(_ArgumentNullExceptionFault_QNAME, ArgumentNullExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LicenseExceptionFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link LicenseExceptionFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "LicenseExceptionFault")
    public JAXBElement<LicenseExceptionFault> createLicenseExceptionFault(LicenseExceptionFault value) {
        return new JAXBElement<LicenseExceptionFault>(_LicenseExceptionFault_QNAME, LicenseExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotSupportedExceptionFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NotSupportedExceptionFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "NotSupportedExceptionFault")
    public JAXBElement<NotSupportedExceptionFault> createNotSupportedExceptionFault(NotSupportedExceptionFault value) {
        return new JAXBElement<NotSupportedExceptionFault>(_NotSupportedExceptionFault_QNAME, NotSupportedExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PathNotFoundExceptionFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PathNotFoundExceptionFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "PathNotFoundExceptionFault")
    public JAXBElement<PathNotFoundExceptionFault> createPathNotFoundExceptionFault(PathNotFoundExceptionFault value) {
        return new JAXBElement<PathNotFoundExceptionFault>(_PathNotFoundExceptionFault_QNAME, PathNotFoundExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnauthorizedAccessFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UnauthorizedAccessFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "UnauthorizedAccessFault")
    public JAXBElement<UnauthorizedAccessFault> createUnauthorizedAccessFault(UnauthorizedAccessFault value) {
        return new JAXBElement<UnauthorizedAccessFault>(_UnauthorizedAccessFault_QNAME, UnauthorizedAccessFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerExceptionFault }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ServerExceptionFault }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI.ConfigurationFaultException", name = "ServerExceptionFault")
    public JAXBElement<ServerExceptionFault> createServerExceptionFault(ServerExceptionFault value) {
        return new JAXBElement<ServerExceptionFault>(_ServerExceptionFault_QNAME, ServerExceptionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Object }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(Object value) {
        return new JAXBElement<Object>(_AnyType_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(String value) {
        return new JAXBElement<String>(_AnyURI_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(byte[] value) {
        return new JAXBElement<byte[]>(_Base64Binary_QNAME, byte[].class, null, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(Boolean value) {
        return new JAXBElement<Boolean>(_Boolean_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Byte }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(Byte value) {
        return new JAXBElement<Byte>(_Byte_QNAME, Byte.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Decimal_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Double }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(Double value) {
        return new JAXBElement<Double>(_Double_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Float }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(Float value) {
        return new JAXBElement<Float>(_Float_QNAME, Float.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(Integer value) {
        return new JAXBElement<Integer>(_Int_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(Long value) {
        return new JAXBElement<Long>(_Long_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link QName }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(QName value) {
        return new JAXBElement<QName>(_QName_QNAME, QName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(Short value) {
        return new JAXBElement<Short>(_Short_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(String value) {
        return new JAXBElement<String>(_String_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Short }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(Short value) {
        return new JAXBElement<Short>(_UnsignedByte_QNAME, Short.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Long }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(Long value) {
        return new JAXBElement<Long>(_UnsignedInt_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnsignedLong_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(Integer value) {
        return new JAXBElement<Integer>(_UnsignedShort_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(Integer value) {
        return new JAXBElement<Integer>(_Char_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Duration }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(Duration value) {
        return new JAXBElement<Duration>(_Duration_QNAME, Duration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(String value) {
        return new JAXBElement<String>(_Guid_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConfigurationItem }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConfigurationItem }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ConfigurationItem")
    public JAXBElement<ConfigurationItem> createConfigurationItem(ConfigurationItem value) {
        return new JAXBElement<ConfigurationItem>(_ConfigurationItem_QNAME, ConfigurationItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfConfigurationItem }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfConfigurationItem }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfConfigurationItem")
    public JAXBElement<ArrayOfConfigurationItem> createArrayOfConfigurationItem(ArrayOfConfigurationItem value) {
        return new JAXBElement<ArrayOfConfigurationItem>(_ArrayOfConfigurationItem_QNAME, ArrayOfConfigurationItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnablePropertyInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnablePropertyInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "EnablePropertyInfo")
    public JAXBElement<EnablePropertyInfo> createEnablePropertyInfo(EnablePropertyInfo value) {
        return new JAXBElement<EnablePropertyInfo>(_EnablePropertyInfo_QNAME, EnablePropertyInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfProperty }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfProperty }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfProperty")
    public JAXBElement<ArrayOfProperty> createArrayOfProperty(ArrayOfProperty value) {
        return new JAXBElement<ArrayOfProperty>(_ArrayOfProperty_QNAME, ArrayOfProperty.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Property }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Property }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "Property")
    public JAXBElement<Property> createProperty(Property value) {
        return new JAXBElement<Property>(_Property_QNAME, Property.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfValueTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfValueTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfValueTypeInfo")
    public JAXBElement<ArrayOfValueTypeInfo> createArrayOfValueTypeInfo(ArrayOfValueTypeInfo value) {
        return new JAXBElement<ArrayOfValueTypeInfo>(_ArrayOfValueTypeInfo_QNAME, ArrayOfValueTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValueTypeInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValueTypeInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ValueTypeInfo")
    public JAXBElement<ValueTypeInfo> createValueTypeInfo(ValueTypeInfo value) {
        return new JAXBElement<ValueTypeInfo>(_ValueTypeInfo_QNAME, ValueTypeInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemTypes }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ItemTypes }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ItemTypes")
    public JAXBElement<ItemTypes> createItemTypes(ItemTypes value) {
        return new JAXBElement<ItemTypes>(_ItemTypes_QNAME, ItemTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateResult }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ValidateResult }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ValidateResult")
    public JAXBElement<ValidateResult> createValidateResult(ValidateResult value) {
        return new JAXBElement<ValidateResult>(_ValidateResult_QNAME, ValidateResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfErrorResult }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfErrorResult }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfErrorResult")
    public JAXBElement<ArrayOfErrorResult> createArrayOfErrorResult(ArrayOfErrorResult value) {
        return new JAXBElement<ArrayOfErrorResult>(_ArrayOfErrorResult_QNAME, ArrayOfErrorResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ErrorResult }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ErrorResult }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ErrorResult")
    public JAXBElement<ErrorResult> createErrorResult(ErrorResult value) {
        return new JAXBElement<ErrorResult>(_ErrorResult_QNAME, ErrorResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfMethodInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfMethodInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfMethodInfo")
    public JAXBElement<ArrayOfMethodInfo> createArrayOfMethodInfo(ArrayOfMethodInfo value) {
        return new JAXBElement<ArrayOfMethodInfo>(_ArrayOfMethodInfo_QNAME, ArrayOfMethodInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MethodInfo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MethodInfo }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "MethodInfo")
    public JAXBElement<MethodInfo> createMethodInfo(MethodInfo value) {
        return new JAXBElement<MethodInfo>(_MethodInfo_QNAME, MethodInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfItemFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfItemFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfItemFilter")
    public JAXBElement<ArrayOfItemFilter> createArrayOfItemFilter(ArrayOfItemFilter value) {
        return new JAXBElement<ArrayOfItemFilter>(_ArrayOfItemFilter_QNAME, ArrayOfItemFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ItemFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ItemFilter")
    public JAXBElement<ItemFilter> createItemFilter(ItemFilter value) {
        return new JAXBElement<ItemFilter>(_ItemFilter_QNAME, ItemFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnableFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EnableFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "EnableFilter")
    public JAXBElement<EnableFilter> createEnableFilter(EnableFilter value) {
        return new JAXBElement<EnableFilter>(_EnableFilter_QNAME, EnableFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfPropertyFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfPropertyFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "ArrayOfPropertyFilter")
    public JAXBElement<ArrayOfPropertyFilter> createArrayOfPropertyFilter(ArrayOfPropertyFilter value) {
        return new JAXBElement<ArrayOfPropertyFilter>(_ArrayOfPropertyFilter_QNAME, ArrayOfPropertyFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PropertyFilter }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PropertyFilter }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "PropertyFilter")
    public JAXBElement<PropertyFilter> createPropertyFilter(PropertyFilter value) {
        return new JAXBElement<PropertyFilter>(_PropertyFilter_QNAME, PropertyFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Operator }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Operator }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/VideoOS.ConfigurationAPI", name = "Operator")
    public JAXBElement<Operator> createOperator(Operator value) {
        return new JAXBElement<Operator>(_Operator_QNAME, Operator.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfstring")
    public JAXBElement<ArrayOfstring> createArrayOfstring(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_ArrayOfstring_QNAME, ArrayOfstring.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfKeyValueOfstringstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfKeyValueOfstringstring")
    public JAXBElement<ArrayOfKeyValueOfstringstring> createArrayOfKeyValueOfstringstring(ArrayOfKeyValueOfstringstring value) {
        return new JAXBElement<ArrayOfKeyValueOfstringstring>(_ArrayOfKeyValueOfstringstring_QNAME, ArrayOfKeyValueOfstringstring.class, null, value);
    }

}
