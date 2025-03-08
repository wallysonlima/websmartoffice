package blockchain_java;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/hyperledger-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.6.2.
 */
@SuppressWarnings("rawtypes")
public class PropertySale extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b5060405161118e38038061118e833981810160405281019061003291906102fd565b336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550846002908161008191906105e3565b508360038190555082600481905550816005908161009f91906105e3565b5080600690816100af91906105e3565b506000600760006101000a81548160ff0219169083151502179055504260088190555060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f4e409a8a2939c293c474b5ce2ef460609b0117d6c1dd735d661dfa5ad21e274c866004546008546040516101409392919061070e565b60405180910390a2505050505061074c565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6101b982610170565b810181811067ffffffffffffffff821117156101d8576101d7610181565b5b80604052505050565b60006101eb610152565b90506101f782826101b0565b919050565b600067ffffffffffffffff82111561021757610216610181565b5b61022082610170565b9050602081019050919050565b60005b8381101561024b578082015181840152602081019050610230565b60008484015250505050565b600061026a610265846101fc565b6101e1565b9050828152602081018484840111156102865761028561016b565b5b61029184828561022d565b509392505050565b600082601f8301126102ae576102ad610166565b5b81516102be848260208601610257565b91505092915050565b6000819050919050565b6102da816102c7565b81146102e557600080fd5b50565b6000815190506102f7816102d1565b92915050565b600080600080600060a086880312156103195761031861015c565b5b600086015167ffffffffffffffff81111561033757610336610161565b5b61034388828901610299565b9550506020610354888289016102e8565b9450506040610365888289016102e8565b935050606086015167ffffffffffffffff81111561038657610385610161565b5b61039288828901610299565b925050608086015167ffffffffffffffff8111156103b3576103b2610161565b5b6103bf88828901610299565b9150509295509295909350565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061041e57607f821691505b602082108103610431576104306103d7565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026104997fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8261045c565b6104a3868361045c565b95508019841693508086168417925050509392505050565b6000819050919050565b60006104e06104db6104d6846102c7565b6104bb565b6102c7565b9050919050565b6000819050919050565b6104fa836104c5565b61050e610506826104e7565b848454610469565b825550505050565b600090565b610523610516565b61052e8184846104f1565b505050565b5b818110156105525761054760008261051b565b600181019050610534565b5050565b601f8211156105975761056881610437565b6105718461044c565b81016020851015610580578190505b61059461058c8561044c565b830182610533565b50505b505050565b600082821c905092915050565b60006105ba6000198460080261059c565b1980831691505092915050565b60006105d383836105a9565b9150826002028217905092915050565b6105ec826103cc565b67ffffffffffffffff81111561060557610604610181565b5b61060f8254610406565b61061a828285610556565b600060209050601f83116001811461064d576000841561063b578287015190505b61064585826105c7565b8655506106ad565b601f19841661065b86610437565b60005b828110156106835784890151825560018201915060208501945060208101905061065e565b868310156106a0578489015161069c601f8916826105a9565b8355505b6001600288020188555050505b505050505050565b600082825260208201905092915050565b60006106d1826103cc565b6106db81856106b5565b93506106eb81856020860161022d565b6106f481610170565b840191505092915050565b610708816102c7565b82525050565b6000606082019050818103600083015261072881866106c6565b905061073760208301856106ff565b61074460408301846106ff565b949350505050565b610a338061075b6000396000f3fe6080604052600436106100a75760003560e01c806366440d711161006457806366440d711461018d5780636f9fb98a146101b85780637150d8ae146101e3578063a6a0bd241461020e578063b90c1dbb14610239578063fc50465514610264576100a7565b806305b34410146100ac57806308551a53146100d75780633503024214610102578063451a308f1461012d5780634a842aae146101375780635ed2c3db14610162575b600080fd5b3480156100b857600080fd5b506100c161028f565b6040516100ce9190610740565b60405180910390f35b3480156100e357600080fd5b506100ec610295565b6040516100f9919061079c565b60405180910390f35b34801561010e57600080fd5b506101176102b9565b6040516101249190610847565b60405180910390f35b610135610347565b005b34801561014357600080fd5b5061014c6105ac565b6040516101599190610740565b60405180910390f35b34801561016e57600080fd5b506101776105b2565b6040516101849190610847565b60405180910390f35b34801561019957600080fd5b506101a2610640565b6040516101af9190610847565b60405180910390f35b3480156101c457600080fd5b506101cd6106ce565b6040516101da9190610740565b60405180910390f35b3480156101ef57600080fd5b506101f86106d6565b604051610205919061079c565b60405180910390f35b34801561021a57600080fd5b506102236106fc565b6040516102309190610884565b60405180910390f35b34801561024557600080fd5b5061024e610713565b60405161025b9190610740565b60405180910390f35b34801561027057600080fd5b5061027961071d565b6040516102869190610740565b60405180910390f35b60085481565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600680546102c6906108ce565b80601f01602080910402602001604051908101604052809291908181526020018280546102f2906108ce565b801561033f5780601f106103145761010080835404028352916020019161033f565b820191906000526020600020905b81548152906001019060200180831161032257829003601f168201915b505050505081565b600760009054906101000a900460ff1615610397576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161038e9061094b565b60405180910390fd5b60045434146103db576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016103d2906109dd565b60405180910390fd5b33600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001600760006101000a81548160ff021916908315150217905550600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f3c0cade9d270c0005f089d49842bd35076264614be1ca87be18f996825d9e07c3460405161049f9190610740565b60405180910390a260008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166108fc349081150290604051600060405180830381858888f1935050505015801561050d573d6000803e3d6000fd5b50600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f7705795180749414bef8ba87ea6d49e0b6eb377c478410086c7343e75da88aa460405160405180910390a3565b60035481565b600580546105bf906108ce565b80601f01602080910402602001604051908101604052809291908181526020018280546105eb906108ce565b80156106385780601f1061060d57610100808354040283529160200191610638565b820191906000526020600020905b81548152906001019060200180831161061b57829003601f168201915b505050505081565b6002805461064d906108ce565b80601f0160208091040260200160405190810160405280929190818152602001828054610679906108ce565b80156106c65780601f1061069b576101008083540402835291602001916106c6565b820191906000526020600020905b8154815290600101906020018083116106a957829003601f168201915b505050505081565b600047905090565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600760009054906101000a900460ff16905090565b6000600854905090565b6000600454905090565b6000819050919050565b61073a81610727565b82525050565b60006020820190506107556000830184610731565b92915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b60006107868261075b565b9050919050565b6107968161077b565b82525050565b60006020820190506107b1600083018461078d565b92915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156107f15780820151818401526020810190506107d6565b60008484015250505050565b6000601f19601f8301169050919050565b6000610819826107b7565b61082381856107c2565b93506108338185602086016107d3565b61083c816107fd565b840191505092915050565b60006020820190508181036000830152610861818461080e565b905092915050565b60008115159050919050565b61087e81610869565b82525050565b60006020820190506108996000830184610875565b92915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806108e657607f821691505b6020821081036108f9576108f861089f565b5b50919050565b7f412070726f7072696564616465206a6120666f692076656e6469646100000000600082015250565b6000610935601c836107c2565b9150610940826108ff565b602082019050919050565b6000602082019050818103600083015261096481610928565b9050919050565b7f56616c6f7220656e766961646f206e616f20636f72726573706f6e646520616f60008201527f20707265636f20646f20696d6f76656c00000000000000000000000000000000602082015250565b60006109c76030836107c2565b91506109d28261096b565b604082019050919050565b600060208201905081810360008301526109f6816109ba565b905091905056fea2646970667358221220247f520770fb3e1e34ffd4ecdc32060c82787dad0d2aa4577547cbca9fec61ae64736f6c634300081c0033";

    private static String librariesLinkedBinary;

    public static final String FUNC_BUYPROPERTY = "buyProperty";

    public static final String FUNC_BUYER = "buyer";

    public static final String FUNC_CREATIONDATE = "creationDate";

    public static final String FUNC_GETCONTRACTBALANCE = "getContractBalance";

    public static final String FUNC_GETCREATIONDATE = "getCreationDate";

    public static final String FUNC_GETPRICEINWEI = "getPriceInWei";

    public static final String FUNC_ISPROPERTYSOLD = "isPropertySold";

    public static final String FUNC_NOTARIALDEED = "notarialDeed";

    public static final String FUNC_PROPERTYADDRESS = "propertyAddress";

    public static final String FUNC_PROPERTYSIZE = "propertySize";

    public static final String FUNC_REGISTERPROPERTY = "registerProperty";

    public static final String FUNC_SELLER = "seller";

    public static final Event PROPERTYLISTED_EVENT = new Event("PropertyListed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event PROPERTYPURCHASED_EVENT = new Event("PropertyPurchased", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SALECOMPLETED_EVENT = new Event("SaleCompleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected PropertySale(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PropertySale(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PropertySale(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PropertySale(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<PropertyListedEventResponse> getPropertyListedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PROPERTYLISTED_EVENT, transactionReceipt);
        ArrayList<PropertyListedEventResponse> responses = new ArrayList<PropertyListedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PropertyListedEventResponse typedResponse = new PropertyListedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.propertyAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.priceInWei = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.creationDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PropertyListedEventResponse getPropertyListedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PROPERTYLISTED_EVENT, log);
        PropertyListedEventResponse typedResponse = new PropertyListedEventResponse();
        typedResponse.log = log;
        typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.propertyAddress = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.priceInWei = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        typedResponse.creationDate = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<PropertyListedEventResponse> propertyListedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPropertyListedEventFromLog(log));
    }

    public Flowable<PropertyListedEventResponse> propertyListedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPERTYLISTED_EVENT));
        return propertyListedEventFlowable(filter);
    }

    public static List<PropertyPurchasedEventResponse> getPropertyPurchasedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(PROPERTYPURCHASED_EVENT, transactionReceipt);
        ArrayList<PropertyPurchasedEventResponse> responses = new ArrayList<PropertyPurchasedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PropertyPurchasedEventResponse typedResponse = new PropertyPurchasedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static PropertyPurchasedEventResponse getPropertyPurchasedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(PROPERTYPURCHASED_EVENT, log);
        PropertyPurchasedEventResponse typedResponse = new PropertyPurchasedEventResponse();
        typedResponse.log = log;
        typedResponse.buyer = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<PropertyPurchasedEventResponse> propertyPurchasedEventFlowable(
            EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getPropertyPurchasedEventFromLog(log));
    }

    public Flowable<PropertyPurchasedEventResponse> propertyPurchasedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPERTYPURCHASED_EVENT));
        return propertyPurchasedEventFlowable(filter);
    }

    public static List<SaleCompletedEventResponse> getSaleCompletedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(SALECOMPLETED_EVENT, transactionReceipt);
        ArrayList<SaleCompletedEventResponse> responses = new ArrayList<SaleCompletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SaleCompletedEventResponse typedResponse = new SaleCompletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static SaleCompletedEventResponse getSaleCompletedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(SALECOMPLETED_EVENT, log);
        SaleCompletedEventResponse typedResponse = new SaleCompletedEventResponse();
        typedResponse.log = log;
        typedResponse.seller = (String) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.buyer = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<SaleCompletedEventResponse> saleCompletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getSaleCompletedEventFromLog(log));
    }

    public Flowable<SaleCompletedEventResponse> saleCompletedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SALECOMPLETED_EVENT));
        return saleCompletedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> buyProperty(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYPROPERTY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<String> buyer() {
        final Function function = new Function(FUNC_BUYER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> creationDate() {
        final Function function = new Function(FUNC_CREATIONDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getContractBalance() {
        final Function function = new Function(FUNC_GETCONTRACTBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getCreationDate() {
        final Function function = new Function(FUNC_GETCREATIONDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getPriceInWei() {
        final Function function = new Function(FUNC_GETPRICEINWEI, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> isPropertySold() {
        final Function function = new Function(FUNC_ISPROPERTYSOLD, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> notarialDeed() {
        final Function function = new Function(FUNC_NOTARIALDEED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> propertyAddress() {
        final Function function = new Function(FUNC_PROPERTYADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> propertySize() {
        final Function function = new Function(FUNC_PROPERTYSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> registerProperty() {
        final Function function = new Function(FUNC_REGISTERPROPERTY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> seller() {
        final Function function = new Function(FUNC_SELLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static PropertySale load(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        return new PropertySale(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PropertySale load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PropertySale(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PropertySale load(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        return new PropertySale(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PropertySale load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PropertySale(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PropertySale> deploy(Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider, String _propertyAddress,
            BigInteger _propertySize, BigInteger priceInWei, String _registerProperty,
            String _notarialDeed) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_propertyAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertySize), 
                new org.web3j.abi.datatypes.generated.Uint256(priceInWei), 
                new org.web3j.abi.datatypes.Utf8String(_registerProperty), 
                new org.web3j.abi.datatypes.Utf8String(_notarialDeed)));
        return deployRemoteCall(PropertySale.class, web3j, credentials, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    public static RemoteCall<PropertySale> deploy(Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider,
            String _propertyAddress, BigInteger _propertySize, BigInteger priceInWei,
            String _registerProperty, String _notarialDeed) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_propertyAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertySize), 
                new org.web3j.abi.datatypes.generated.Uint256(priceInWei), 
                new org.web3j.abi.datatypes.Utf8String(_registerProperty), 
                new org.web3j.abi.datatypes.Utf8String(_notarialDeed)));
        return deployRemoteCall(PropertySale.class, web3j, transactionManager, contractGasProvider, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PropertySale> deploy(Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit, String _propertyAddress,
            BigInteger _propertySize, BigInteger priceInWei, String _registerProperty,
            String _notarialDeed) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_propertyAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertySize), 
                new org.web3j.abi.datatypes.generated.Uint256(priceInWei), 
                new org.web3j.abi.datatypes.Utf8String(_registerProperty), 
                new org.web3j.abi.datatypes.Utf8String(_notarialDeed)));
        return deployRemoteCall(PropertySale.class, web3j, credentials, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PropertySale> deploy(Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
            String _propertyAddress, BigInteger _propertySize, BigInteger priceInWei,
            String _registerProperty, String _notarialDeed) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_propertyAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(_propertySize), 
                new org.web3j.abi.datatypes.generated.Uint256(priceInWei), 
                new org.web3j.abi.datatypes.Utf8String(_registerProperty), 
                new org.web3j.abi.datatypes.Utf8String(_notarialDeed)));
        return deployRemoteCall(PropertySale.class, web3j, transactionManager, gasPrice, gasLimit, getDeploymentBinary(), encodedConstructor);
    }

//    public static void linkLibraries(List<Contract.LinkReference> references) {
//        librariesLinkedBinary = linkBinaryWithReferences(BINARY, references);
//    }

    private static String getDeploymentBinary() {
        if (librariesLinkedBinary != null) {
            return librariesLinkedBinary;
        } else {
            return BINARY;
        }
    }

    public static class PropertyListedEventResponse extends BaseEventResponse {
        public String seller;

        public String propertyAddress;

        public BigInteger priceInWei;

        public BigInteger creationDate;
    }

    public static class PropertyPurchasedEventResponse extends BaseEventResponse {
        public String buyer;

        public BigInteger amount;
    }

    public static class SaleCompletedEventResponse extends BaseEventResponse {
        public String seller;

        public String buyer;
    }
}
