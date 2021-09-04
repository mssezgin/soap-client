package com.sezgin.mywsclient;

import com.sezgin.mywsclient.wsdl.ClientSoapHeaders;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class SecurityHeader implements WebServiceMessageCallback {

    private ClientSoapHeaders clientSoapHeaders;

    public SecurityHeader(ClientSoapHeaders clientSoapHeaders) {
        this.clientSoapHeaders = clientSoapHeaders;
    }

    @Override
    public void doWithMessage(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
        SoapHeader soapHeader = ((SoapMessage) webServiceMessage).getSoapHeader();
        try {
            JAXBContext context = JAXBContext.newInstance(ClientSoapHeaders.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(this.clientSoapHeaders, soapHeader.getResult());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
