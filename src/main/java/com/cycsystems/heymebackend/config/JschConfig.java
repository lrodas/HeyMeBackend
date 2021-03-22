package com.cycsystems.heymebackend.config;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Configuration
public class JschConfig {

	private Logger LOG = LogManager.getLogger(JschConfig.class);

	@Value("${fileServer.user}")
	private String user;

	@Value("${fileServer.pass}")
	private String pass;

	@Value("${fileServer.host}")
	private String host;

	@Value("${fileServer.port}")
	private Integer port;

	public ChannelSftp saveFile(String path, InputStream file, String name) throws JSchException, SftpException {
		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(pass);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();
			LOG.info("Coneccion abierta a servidor");
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.cd(path);

			channelSftp.put(file, name, ChannelSftp.OVERWRITE);
			LOG.info("Archivo guardado en servidor");

			channelSftp.disconnect();
			session.disconnect();
			LOG.info("Coneccion y session cerrada");

		} catch (Exception e) {
			channelSftp = null;
		} finally {
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}

			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}

		return channelSftp;

	}

	public ByteArrayOutputStream obtainFile(String path) throws JSchException, SftpException {
		Session session = null;
		ChannelSftp channelSftp = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(pass);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");

			session.setConfig(config);
			session.connect();
			LOG.info("Coneccion abierta a servidor");
			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();

			BufferedOutputStream buff = new BufferedOutputStream(outputStream);

			channelSftp.get(path, buff);
			LOG.info("Obtiene archivo guardado en servidor");

			channelSftp.disconnect();
			session.disconnect();
			LOG.info("Coneccion y session cerrada");

		} catch (Exception e) {
			outputStream = null;
		} finally {

			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}

			if (session != null && session.isConnected()) {
				session.disconnect();
			}

		}

		return outputStream;

	}

}
