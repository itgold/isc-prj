package com.isc.integration.mip

import com.iscweb.integration.cameras.mip.services.streaming.commands.ConnectCommand
import com.iscweb.integration.cameras.mip.services.streaming.commands.ConnectResponse
import com.iscweb.integration.cameras.mip.services.streaming.commands.LiveCommand
import com.iscweb.integration.cameras.mip.services.streaming.commands.LiveCommandStatus
import com.iscweb.integration.cameras.mip.utils.XmlUtils
import spock.lang.Specification

import javax.xml.bind.JAXBException


class ImageServerTest extends Specification {

    def "testMarshalling"() {
        when:
            def connectCommand = new ConnectCommand()
            connectCommand.setConnectParameters('[CAMERA]', '[STREAM]', '[TOKEN]')
            def xml = XmlUtils.toXml(connectCommand)
            def connectCommandCopy = fromXml(xml)

        then:
            xml != null && xml.size() > 0
            connectCommandCopy != null
            connectCommandCopy.cameraId == '[CAMERA]'
            connectCommandCopy.cameraId == connectCommand.cameraId

            connectCommandCopy.requestId == connectCommand.requestId
            connectCommandCopy.methodName == connectCommand.methodName
            connectCommandCopy.methodName == 'connect'
    }

    def "testUnmarshalling"() {
        when:
            def connectResponseXml = '''<?xml version="1.0" encoding="UTF-8"?>
                <methodresponse>
                  <requestid>100</requestid>
                  <methodname>connect</methodname>
                  <connected>yes</connected>
                  <errorreason>Error Reason</errorreason>
                  <alwaysstdjpeg>true</alwaysstdjpeg>
                  <camera>some camera details</camera>
                  <clientcapabilities>
                     <privacymask>privacymask</privacymask>
                     <privacymaskversion>privacymaskversion</privacymaskversion>
                     <multipartdata>multipartdata</multipartdata>
                     <datarestriction>datarestriction</datarestriction>
                  </clientcapabilities>
                  <servercapabilities>
                     <connectupdate>connectupdate</connectupdate>
                     <adaptivestreamingversion>adaptivestreamingversion</adaptivestreamingversion>
                  </servercapabilities>
                  <transcode>
                     <allframes>false</allframes>
                     <width>width</width>
                     <height>height</height>
                    <keepaspectratio>true</keepaspectratio>
                     <allowupsizing>false</allowupsizing>
                  </transcode>
                </methodresponse>
            '''

            def connectResponse = XmlUtils.fromXml(connectResponseXml, ConnectResponse)

        then:
            connectResponse != null
            connectResponse.requestId == '100'
            connectResponse.methodName == 'connect'
            connectResponse.connected == true
            connectResponse.errorReason == 'Error Reason'
    }

    def "testUnmarshallStatus"() {
        when:
            def connectResponseXml = '''<?xml version="1.0" encoding="utf-8"?>
            <livepackage>
                <status>
                    <statustime>1605074397121</statustime>
                    <statusitem id="2" value="0" />
                    <statusitem id="3" value="0" />
                    <statusitem id="5" value="1" />
                </status>
            </livepackage>
            '''

            def status = XmlUtils.fromXml(connectResponseXml, LiveCommandStatus)

        then:
            status != null
    }

    def "testMarshallLiveCommand"() {
        when:
            def requestXml = '''<?xml version="1.0"  encoding="UTF-8"?>
                <methodcall>
                  <requestid>1</requestid>
                  <methodname>live</methodname>
                  <compressionrate>90</compressionrate>
                  <sendinitialimage>no</sendinitialimage>
                  <attributes framerate="full" motiononly="true" />
                  <adaptivestreaming>
                    <resolution>
                      <widthhint>100</widthhint>
                      <heighthint>100</heighthint>
                    </resolution>
                    <maxresolution/>
                    <disabled/>
                  </adaptivestreaming>
                </methodcall>
            '''

            def request = XmlUtils.fromXml(requestXml, LiveCommand)

        then:
            request != null
            request.requestId == '1'
            request.methodName == 'live'
            request.attributes != null
            request.attributes.frameRate == 'full'
            request.attributes.motionOnly == true
            request.adaptiveStreaming != null
            request.adaptiveStreaming.resolution != null
            request.adaptiveStreaming.resolution.widthHint == 100
            request.adaptiveStreaming.resolution.heightHint == 100
    }

    // this is workaround to let groovy to resolve types properly
    def fromXml(String xml) throws JAXBException {
        return (ConnectCommand) XmlUtils.fromXml(xml, ConnectCommand)
    }
}