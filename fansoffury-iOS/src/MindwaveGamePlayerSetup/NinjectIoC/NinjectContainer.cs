﻿using System;
using XLabs.Ioc;
using System.Diagnostics.CodeAnalysis;
using Ninject;
using XLabs.Ioc.Ninject;

namespace NinjectIoC
{
	/// <summary>
	/// The Ninject container.
	/// this class was created based on XLabs class but has a fix in it. Pull request has been submitted.
	/// </summary>
	[SuppressMessage("StyleCop.CSharp.DocumentationRules", "SA1650:ElementDocumentationMustBeSpelledCorrectly", Justification = "Reviewed. Suppression is OK here.")]
	public class NinjectContainerNew : IDependencyContainer
	{
		private readonly IKernel kernel;
		private readonly IResolver resolver;

		/// <summary>
		/// Initializes a new instance of the <see cref="NinjectContainer"/> class.
		/// </summary>
		/// <param name="kernel">
		/// The kernel.
		/// </param>
		public NinjectContainerNew(IKernel kernel)
		{
			this.kernel = kernel;
			this.resolver = new NinjectResolver(kernel);
		}

		/// <summary>
		/// Initializes a new instance of the <see cref="NinjectContainer"/> class with <see cref="StandardKernel"/>.
		/// </summary>
		public NinjectContainerNew() : this(new StandardKernel())
		{
		}

		/// <summary>
		/// Gets the resolver from the container.
		/// </summary>
		/// <returns>An instance of <see cref="IResolver"/></returns>
		public IResolver GetResolver()
		{
			return this.resolver;
		}

		/// <summary>
		/// Registers an instance of T to be stored in the container.
		/// </summary>
		/// <typeparam name="T">Type of instance.</typeparam>
		/// <param name="instance">Instance of type T.</param>
		/// <returns>An instance of <see cref="IDependencyContainer"/></returns>
		public IDependencyContainer Register<T>(T instance) where T : class
		{
			this.kernel.Bind<T>().ToConstant<T>(instance);
			return this;
		}

		/// <summary>
		/// Registers a type to instantiate for type T.
		/// </summary>
		/// <typeparam name="T">Type of instance.</typeparam>
		/// <typeparam name="TImpl">Type to register for instantiation.</typeparam>
		/// <returns>An instance of <see cref="IDependencyContainer"/></returns>
		public IDependencyContainer Register<T, TImpl>()
			where T : class
			where TImpl : class, T
		{
			if (typeof (T) == typeof (TImpl))
			{
				this.kernel.Bind<T>().ToSelf();
			}
			else
			{
				this.kernel.Bind<T>().To<TImpl>();
			}

			return this;
		}

		/// <summary>
		/// Registers a type to instantiate for type T as singleton.
		/// </summary>
		/// <typeparam name="T">Type of instance.</typeparam>
		/// <typeparam name="TImpl">Type to register for instantiation.</typeparam>
		/// <returns>An instance of <see cref="IDependencyContainer"/></returns>
		public IDependencyContainer RegisterSingle<T, TImpl>()
			where T : class
			where TImpl : class, T
		{
			this.kernel.Bind<T>().To<TImpl>().InSingletonScope();
			return this;
		}

		/// <summary>
		/// Tries to register a type.
		/// </summary>
		/// <typeparam name="T">Type of instance.</typeparam>
		/// <param name="type">Type of implementation.</param>
		/// <returns>An instance of <see cref="IDependencyContainer"/></returns>
		public IDependencyContainer Register<T>(Type type) where T : class
		{
			this.kernel.Bind<T>().To(type);
			return this;
		}

		/// <summary>
		/// Tries to register a type.
		/// </summary>
		/// <param name="type">Type to register.</param>
		/// <param name="impl">Type that implements registered type.</param>
		/// <returns>An instance of <see cref="IDependencyContainer"/></returns>
		public IDependencyContainer Register(Type type, Type impl)
		{
			this.kernel.Bind(type).To(impl);
			return this;
		}

		/// <summary>
		/// Registers a function which returns an instance of type T.
		/// </summary>
		/// <typeparam name="T">Type of instance.</typeparam>
		/// <param name="func">Function which returns an instance of T.</param>
		/// <returns>An instance of <see cref="IDependencyContainer"/></returns>
		public IDependencyContainer Register<T>(Func<IResolver, T> func) where T : class
		{
			this.kernel.Bind<T>().ToMethod<T>(t => func(this.resolver));
			return this;
		}
	}
}

