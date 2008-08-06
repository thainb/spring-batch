package org.springframework.batch.item.database;

import org.springframework.batch.item.sample.Foo;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.CommonItemStreamItemReaderTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.junit.runner.RunWith;
import org.junit.Test;

import javax.persistence.EntityManagerFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JpaPagingItemReaderCommonTests extends CommonItemStreamItemReaderTests {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	protected ItemReader<Foo> getItemReader() throws Exception {

		String jpqlQuery = "select f from Foo f";

		JpaPagingItemReader<Foo> reader = new JpaPagingItemReader<Foo>();
		reader.setQueryString(jpqlQuery);
		reader.setEntityManagerFactory(entityManagerFactory);
		reader.setPageSize(3);
		reader.afterPropertiesSet();
		reader.setSaveState(true);

		return reader;
	}

	protected void pointToEmptyInput(ItemReader<Foo> tested) throws Exception {
		JpaPagingItemReader<Foo> reader = (JpaPagingItemReader<Foo>) tested;
		reader.close(new ExecutionContext());
		reader.setQueryString("select f from Foo f where f.id = -1");
		reader.afterPropertiesSet();
		reader.open(new ExecutionContext());
	}


	@Transactional @Test
	public void testRestart() throws Exception {
		super.testRestart();
	}

	@Transactional @Test
	public void testResetAndRestart() throws Exception {
		super.testResetAndRestart();
	}

	@Transactional @Test
	public void testReopen() throws Exception {
		super.testReopen();
	}

	@Transactional @Test
	public void testRead() throws Exception {
		super.testRead();
	}

	@Transactional @Test
	public void testReset() throws Exception {
		super.testReset();
	}

	@Transactional @Test
	public void testEmptyInput() throws Exception {
		super.testEmptyInput();
	}
}